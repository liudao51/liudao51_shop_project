package com.liudao51.shop.common.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * class: 读取资源文件的配置工具类
 */
public class PropertiesUtilsX {
    /**
     * 通过指定资源文件名获取资源对象
     *
     * @param location
     * @return
     */
    public static Properties getLocationProperties(String location) {
        Properties properties = null;
        try {
            properties = PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource(location), "UTF-8"));

        } catch (IOException ioe) {
            throw new RuntimeException("资源文件读取错误");
        }

        return properties;
    }

    /**
     * 通过默认资源文件名(application.properties)获取资源对象
     *
     * @return
     */
    public static Properties getDefaultProperties() {
        String profilesActive = (PropertiesUtilsX.getLocationProperties("application.properties")).getProperty("spring.profiles.active");
        String location = (profilesActive != null && profilesActive != "") ? "application-" + profilesActive + ".properties" : "application.properties";

        return PropertiesUtilsX.getLocationProperties(location);
    }

}
