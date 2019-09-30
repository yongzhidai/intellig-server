package com.dyz.intellig.service.intelligserver;

import com.dyz.intellig.service.intelligserver.tcp.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

@SpringBootApplication
@MapperScan("com.dyz.intellig.service.intelligserver.dao")
public class IntelligServerApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(IntelligServerApplication.class);

	@Value("${netty.port}")
	private Integer port;
	@Autowired
	private NettyServer nettyServer;


	public static void main(String[] args) {
		SpringApplication.run(IntelligServerApplication.class, args);
	}


	private static void testReadConfig(){
		try {
			LOGGER.info("---user.dir-----"+System.getProperty("user.dir"));
			Properties properties = new Properties();
			properties.load(new InputStreamReader(new FileInputStream("./config/config.properties")));
			String configVal = properties.getProperty("test.user.dir");
			LOGGER.info("---config---"+ configVal);
		}catch (Exception e){
			LOGGER.error("error.",e);
		}
	}


	@Override
	public void run(String... args) throws Exception {
	    testReadConfig();
		LOGGER.info(".........netty server starting......");
		nettyServer.start(port);
		LOGGER.info(".........netty server started and with port:{} ......",port);
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				LOGGER.info(".........netty server stoping......");
				try {
					nettyServer.destory();
				}catch (Exception e){
					LOGGER.info(".........netty server stop error: ",e);
				}
				LOGGER.info(".........netty server stoped......");

			}
		});
	}
}
