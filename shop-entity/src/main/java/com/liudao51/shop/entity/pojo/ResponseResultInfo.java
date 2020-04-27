package com.liudao51.shop.entity.pojo;

import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.exception.AppException;
import com.liudao51.shop.common.util.StringUtilsX;

import java.io.Serializable;

/**
 * 客户端响应结果实体类
 */
public class ResponseResultInfo<T> implements Serializable {

    // 状态码
    private Integer code;
    // 消息内容
    private String message;
    // 时间戮
    private Long timestamp;
    // 数据内容
    private T data;

    // 返回处理成功结果
    public ResponseResultInfo() {
        this.code = ErrorCode.SUCCESS.getCode();
        this.message = ErrorCode.SUCCESS.getMessage();
        this.timestamp = System.currentTimeMillis();
        this.data = null;
    }

    // 返回处理成功结果
    public ResponseResultInfo(T data) {
        this.code = ErrorCode.SUCCESS.getCode();
        this.message = ErrorCode.SUCCESS.getMessage();
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    // 返回处理失败结果
    public ResponseResultInfo(ErrorCode errorCode, T data) {
        this.code = (errorCode != null
                && errorCode.getCode() != null
                && !StringUtilsX.isBlank(errorCode.getCode().toString())
        ) ? errorCode.getCode() : ErrorCode.FAIL.getCode();
        this.message = (errorCode != null
                && errorCode.getMessage() != null
                && !StringUtilsX.isBlank(errorCode.getMessage())
        ) ? errorCode.getMessage() : ErrorCode.FAIL.getMessage();
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    // 返回处理失败结果
    public ResponseResultInfo(AppException ae, T data) {
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
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    // 返回处理具体结果(成功或失败)
    public ResponseResultInfo(Integer code, String message, T data) {
        this.code = (code != null && !StringUtilsX.isBlank(code.toString())) ? code : ErrorCode.FAIL.getCode();
        this.message = (message != null && !StringUtilsX.isBlank(message)) ? message : ErrorCode.FAIL.getMessage();
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public T getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return "ResponseResultInfo={"
                + "code:" + this.code
                + ", message:'" + this.message
                + ", timestamp:'" + this.timestamp
                + ", data:'" + this.data.toString()
                + "}";
    }
}