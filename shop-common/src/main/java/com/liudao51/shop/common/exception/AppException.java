package com.liudao51.shop.common.exception;

import com.liudao51.shop.common.constant.ErrorCode;

/**
 * 自定义程序异常
 */
public class AppException extends RuntimeException {

    private ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
