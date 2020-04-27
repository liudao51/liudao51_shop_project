package com.liudao51.shop.common.exception;

import com.liudao51.shop.common.constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 异常工具抛出类
 */
@Slf4j
public class ExceptionUtilsX {

    //抛出异常
    public static void cast(ErrorCode errorCode) {
        //记录id
        log.error(errorCode.toString());

        throw new AppException(errorCode);
    }
}

