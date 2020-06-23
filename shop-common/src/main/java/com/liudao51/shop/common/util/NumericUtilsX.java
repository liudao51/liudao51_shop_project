package com.liudao51.shop.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * class:
 * <p>
 * Created by jewel on 2019/4/23.
 */
public class NumericUtilsX {

    public static final Integer NUMERIC_TYPE_INTEGER = 1;
    public static final Integer NUMERIC_TYPE_DOUBLE = 2;

    /**
     * 检查是否为数字
     *
     * @param val
     * @return
     */
    public static boolean isNumeric(Object val) {
        if (null != val && !StringUtils.isEmpty(val.toString()) && !StringUtils.isBlank(val.toString())) {
            return val.toString().matches("^[-\\+]?[0-9.]*$");
        }
        return false;
    }

    /**
     * 检查是否为特定类型的数字
     *
     * @param val
     * @param numericType
     * @return
     */
    public static boolean isNumeric(Object val, Integer numericType) {
        if (null != val && !StringUtils.isEmpty(String.valueOf(val)) && !StringUtils.isBlank(String.valueOf(val))) {
            switch (numericType) {
                case 1:
                    return val.toString().matches("^[-\\+]?[0-9]*$");
                case 2:
                    return val.toString().matches("^[-\\+]?[0-9.]*$");
            }
        }
        return false;
    }

    /**
     * 转换为n位小数,返回String
     *
     * @param val     要转换前的值
     * @param decimal 要保留的小数位数
     * @param isRound 是否四舍五入
     * @return String
     */
    public static String toString(Double val, int decimal, boolean isRound) {
        DecimalFormat df = new DecimalFormat("0.00"); //默认保留2位小数
        switch (decimal) {
            case 0:
                df = new DecimalFormat("0");
                break;
            case 1:
                df = new DecimalFormat("0.0");
                break;
            case 2:
                df = new DecimalFormat("0.00");
                break;
            case 3:
                df = new DecimalFormat("0.000");
                break;
            case 4:
                df = new DecimalFormat("0.0000");
                break;
            case 5:
                df = new DecimalFormat("0.00000");
                break;
            case 6:
                df = new DecimalFormat("0.000000");
                break;
        }

        //是否需要四舍五入
        if (isRound) {
            df.setRoundingMode(RoundingMode.HALF_UP);
        } else {
            df.setRoundingMode(RoundingMode.DOWN);
        }

        return df.format(val);
    }

    /**
     * 转换为n位小数,返回Double(数值最右边的0会省略返回)
     *
     * @param val     要转换前的值
     * @param decimal 要保留的小数位数
     * @param isRound 是否四舍五入
     * @return Double
     */
    public static Double round(Double val, int decimal, boolean isRound) {
        return Double.valueOf(toString(val, decimal, isRound));
    }
}
