package com.taoyuanx.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * @author dushitaoyuan
 * @date 2019/9/11 11:11
 * @desc: 系统配置获取
 */
public final class PropertiesUtil {
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtil.class);

    private  static  Properties SYSTEM_CONFIG=null;
    // 获取非默认配置文件
    public static Properties loadProperties(String configPath) throws IOException {
        if (configPath.startsWith("classpath:")) {
            configPath = configPath.substring(configPath.indexOf("classpath:") + 10);
            Resource resource = new ClassPathResource(configPath);
            EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
            return PropertiesLoaderUtils.loadProperties(encodedResource);
        } else {
            EncodedResource encodedResource = new EncodedResource(new FileSystemResource(new File(configPath)),
                    "UTF-8");
            return PropertiesLoaderUtils.loadProperties(encodedResource);
        }
    }
    // 设置系统配置
    public static synchronized void initSystemConfig(Properties systemConfig) {
        SYSTEM_CONFIG=systemConfig;
    }
    // 获取系统配置
    public static String getSystemProperty(String key) {
        if(SYSTEM_CONFIG==null){
            throw new RuntimeException("未指定系统配置");
        }
        return SYSTEM_CONFIG.getProperty(key);
    }


}
