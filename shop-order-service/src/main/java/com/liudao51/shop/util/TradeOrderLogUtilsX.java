package com.liudao51.shop.util;

import com.liudao51.shop.common.util.CollectionUtilsX;
import com.liudao51.shop.common.util.PropertiesUtilsX;
import com.liudao51.shop.common.util.StringUtilsX;
import lombok.extern.slf4j.Slf4j;

/**
 * 日志工具类
 */
@Slf4j
public class TradeOrderLogUtilsX {
    private static String getLogLevel() {
        return StringUtilsX.getString(PropertiesUtilsX.getDefaultProperties().getProperty("app.log.level"), "error");
    }

    public static void info(String val) {
        if (CollectionUtilsX.isArrayContainsValue(StringUtilsX.split(StringUtilsX.lowerCase(TradeOrderLogUtilsX.getLogLevel()), ","), "info")) {
            log.info(val);
        }
    }

    public static void warn(String val) {
        if (CollectionUtilsX.isArrayContainsValue(StringUtilsX.split(StringUtilsX.lowerCase(TradeOrderLogUtilsX.getLogLevel()), ","), "warn")) {
            log.warn(val);
        }
    }

    public static void error(String val) {
        if (CollectionUtilsX.isArrayContainsValue(StringUtilsX.split(StringUtilsX.lowerCase(TradeOrderLogUtilsX.getLogLevel()), ","), "error")) {
            log.error(val);
        }
    }

    public static void error(String val, Exception e){
        if (CollectionUtilsX.isArrayContainsValue(StringUtilsX.split(StringUtilsX.lowerCase(TradeOrderLogUtilsX.getLogLevel()), ","), "error")) {
            log.error(val);
            e.printStackTrace();
        }
    }

    public static void debug(String val) {
        if (CollectionUtilsX.isArrayContainsValue(StringUtilsX.split(StringUtilsX.lowerCase(TradeOrderLogUtilsX.getLogLevel()), ","), "debug")) {
            log.debug(val);  //如果要想log.debug起作用,则必须在properties文件中配置logging.level.root=DEBUG
        }
    }
}
