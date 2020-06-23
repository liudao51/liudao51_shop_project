package com.liudao51.shop.common.exception;

import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.result.ResponseResult;
import com.liudao51.shop.common.result.ServiceResult;
import lombok.extern.slf4j.Slf4j;

/**
 * 异常工具抛出类
 */
@Slf4j
public class ExceptionUtilsX {

    //抛出异常
    public static void cast(ErrorCode errorCode) {
        throw new AppException(errorCode);
    }

    public static void cast(Integer code, String msg) {
        throw new AppException(code, msg);
    }

    public static String toString(Exception e) {
        return e.getMessage();
    }

    public static String toString(AppException e) {
        return e.toString();
    }

    public static String toString(ErrorCode errorCode) {
        return errorCode.toString();
    }

    public static String toString(ServiceResult result) {
        return result.toString();
    }

    public static String toString(ResponseResult result) {
        return result.toString();
    }
}

