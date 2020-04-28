package com.liudao51.shop.entity.pojo;

import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.util.StringUtilsX;

import java.io.Serializable;

/**
 * 服务生产者端响应结果实体类
 */
public class ServiceResultInfo<T> implements Serializable {

    // 状态码
    private Integer code;
    // 消息内容
    private String message;
    // 时间戮
    private Long timestamp;
    // 数据内容
    private T data;

    public ServiceResultInfo() {
    }

    // 返回处理具体结果(成功或失败)
    public ServiceResultInfo(ErrorCode errorCode) {
        this.code = (errorCode != null
                && errorCode.getCode() != null
                && !StringUtilsX.isBlank(errorCode.getCode().toString())
        ) ? errorCode.getCode() : ErrorCode.FAIL.getCode();

        this.message = (errorCode != null
                && errorCode.getMessage() != null
                && !StringUtilsX.isBlank(errorCode.getMessage())
        ) ? errorCode.getMessage() : ErrorCode.FAIL.getMessage();

        this.timestamp = System.currentTimeMillis();

        this.data = null;
    }

    // 返回处理具体结果(成功或失败)
    public ServiceResultInfo(Integer code, String message) {
        this.code = (code != null
                && !StringUtilsX.isBlank(code.toString())
        ) ? code : ErrorCode.FAIL.getCode();

        this.message = (message != null
                && !StringUtilsX.isBlank(message)
        ) ? message : ErrorCode.FAIL.getMessage();

        this.timestamp = System.currentTimeMillis();

        this.data = null;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ServiceResultInfo={"
                + "code:" + this.code
                + ", message:'" + this.message
                + ", timestamp:'" + this.timestamp
                + ", data:'" + this.data.toString()
                + "}";
    }
}