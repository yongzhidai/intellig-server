package com.dyz.intellig.service.intelligserver;

import com.dyz.intellig.service.intelligserver.common.config.ConfigManager;
import com.dyz.intellig.service.intelligserver.tcp.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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


	@Override
	public void run(String... args) throws Exception {
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
