package com.dyz.intellig.service.intelligserver.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by daiyongzhi on 2019/10/13.
 */
public class ConfigManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigManager.class);

    private static Properties properties = new Properties();

    static {
       try {
           String userDir = System.getProperty("user.dir");
           File configFile = new File(userDir+"/config","config.properties");

           properties.load(new InputStreamReader(new FileInputStream(configFile)));
       }catch (Exception e){
           LOGGER.error("读取配置文件失败",e);
           throw new RuntimeException(e);
       }
    }

    public static String getVal(String key){
        return properties.getProperty(key);
    }
}
