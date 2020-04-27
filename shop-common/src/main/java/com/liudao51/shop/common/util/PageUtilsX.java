package com.liudao51.shop.common.util;

/**
 * 分页工具类
 */
public class PageUtilsX {
    private static final Integer DEFAULT_PAGE_NO = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    @SuppressWarnings("unchecked")
    public static Integer getPageNo(Object val) {
        return (NumericUtilsX.isNumeric(val, NumericUtilsX.NUMERIC_TYPE_INTEGER) && Integer.valueOf(val.toString()) > 0) ?
                Integer.valueOf(val.toString()) : DEFAULT_PAGE_NO;
    }

    @SuppressWarnings("unchecked")
    public static Integer getPageNo(Object val, Integer defaultPageNo) {
        return (NumericUtilsX.isNumeric(val, NumericUtilsX.NUMERIC_TYPE_INTEGER) && Integer.valueOf(val.toString()) > 0) ?
                Integer.valueOf(val.toString()) : defaultPageNo;
    }

    @SuppressWarnings("unchecked")
    public static Integer getPageSize(Object val) {
        return (NumericUtilsX.isNumeric(val, NumericUtilsX.NUMERIC_TYPE_INTEGER) && Integer.valueOf(val.toString()) > 0) ?
                Integer.valueOf(val.toString()) : DEFAULT_PAGE_SIZE;
    }

    @SuppressWarnings("unchecked")
    public static Integer getPageSize(Object val, Integer defaultPageSize) {
        return (NumericUtilsX.isNumeric(val, NumericUtilsX.NUMERIC_TYPE_INTEGER) && Integer.valueOf(val.toString()) > 0) ?
                Integer.valueOf(val.toString()) : defaultPageSize;
    }
}