package com.liudao51.shop.entity.pojo;

import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.AppException;
import com.liudao51.shop.common.util.StringUtilsX;

import java.io.Serializable;

/**
 * 函数调用结果实体类
 */
public class ResultInfo implements Serializable {
    private Integer code;
    private String message;

    // 返回处理成功结果
    public ResultInfo() {
        this.code = ErrorCode.SUCCESS.getCode();
        this.message = ErrorCode.SUCCESS.getMessage();
    }

    // 返回处理失败结果
    public ResultInfo(ErrorCode errorCode) {
        this.code = (errorCode != null
                && errorCode.getCode() != null
                && !StringUtilsX.isBlank(errorCode.getCode().toString())
        ) ? errorCode.getCode() : ErrorCode.FAIL.getCode();
        this.message = (errorCode != null
                && errorCode.getMessage() != null
                && !StringUtilsX.isBlank(errorCode.getMessage())
        ) ? errorCode.getMessage() : ErrorCode.FAIL.getMessage();
    }

    // 返回处理失败结果
    public ResultInfo(AppException ae) {
        this.code = (ae != null
                && ae.getErrorCode() != null
                && ae.getErrorCode().getCode() != null
                && !StringUtilsX.isBlank(ae.getErrorCode().getCode().toString())
        ) ? ae.getErrorCode().getCode() : ErrorCode.FAIL.getCode();
        this.message = (ae != null
                && ae.getErrorCode() != null
                && ae.getErrorCode().getMessage() != null
                && !StringUtilsX.isBlank(ae.getErrorCode().getMessage())
        ) ? ae.getErrorCode().getMessage() : ErrorCode.FAIL.getMessage();
    }

    // 返回处理具体结果(成功或失败)
    public ResultInfo(Integer code, String message) {
        this.code = (code != null && !StringUtilsX.isBlank(code.toString())) ? code : ErrorCode.FAIL.getCode();
        this.message = (message != null && !StringUtilsX.isBlank(message)) ? message : ErrorCode.FAIL.getMessage();
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "ResultInfo={"
                + "code:" + this.code
                + ", message:'" + this.message
                + "}";
    }
}
