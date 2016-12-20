package com.kaishengit.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by loveoh on 2016/12/15.
 */
public class Config {
    private static Logger logger = LoggerFactory.getLogger(Config.class);
    private static Properties prop = new Properties();

    static{
        try {
            prop.load(Config.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            logger.error("读取config配置文件错误");
            throw new RuntimeException("读取config配置文件错误",e);
        }
    }

    public static String get(String key){


        return prop.getProperty(key);
    }
}
