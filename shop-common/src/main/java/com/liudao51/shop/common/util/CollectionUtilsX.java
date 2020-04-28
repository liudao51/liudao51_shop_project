package com.liudao51.shop.common.util;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TODO: 集合工具类
 *
 * @author jewel.liu
 * @since 1.0, Sep 10, 2018
 */
public class CollectionUtilsX {
    private CollectionUtilsX() {
    }

    /**
     * TODO: 检查指定元素是否在数组中
     *
     * @param container
     * @param value
     * @param <T>
     * @return
     */
    public static <T> boolean isArrayContainsValue(T[] container, T value) {
        return Arrays.asList(container).contains(value);
    }

    /**
     * TODO: 检查指定元素数组是否在数组中
     *
     * @param container
     * @param array
     * @param <T>
     * @return
     */
    public static <T> boolean isArrayContainsArray(T[] container, T[] array) {
        Set<T> containerSet = new HashSet(Arrays.asList(container));
        int containerSize = containerSet.size();
        containerSet.addAll(Arrays.asList(array));

        // container包含array之后,containerSet大小是否有所增大
        return (containerSet.size() == containerSize);
    }

    /**
     * TODO: 把map转换为JSONObject
     *
     * @param map
     * @return
     */
    public static JSONObject mapToJson(Map map) {
        JSONObject jsonObj = new JSONObject(map);

        return jsonObj;
    }
}
