package com.liudao51.shop.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * class: 字符串工具类
 * <p>
 * Created by jewel on 2019/4/19.
 */
public class StringUtilsX extends StringUtils {

    /**
     * function: URL字符串解码
     *
     * @param val
     * @return
     * @throws Exception
     */
    public static String urlDecode(String val) throws Exception {
        return java.net.URLDecoder.decode(val, "utf-8");
    }

    /**
     * function: 当取不到值时返回默认值
     *
     * @param val        Object
     * @param defaultVal String
     * @return String
     */
    public static String getString(String val, String defaultVal) {
        return (!isEmpty(val) && !isBlank(val)) ? val : defaultVal;
    }
}
