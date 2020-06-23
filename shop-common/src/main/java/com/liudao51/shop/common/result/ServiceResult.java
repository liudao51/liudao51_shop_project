package com.liudao51.shop.common.result;

import com.liudao51.shop.common.constant.ErrorCode;
import com.liudao51.shop.common.util.StringUtilsX;

import java.io.Serializable;

/**
 * 服务生产者端响应结果实体类
 */
public class ServiceResult<T> implements Serializable {

    // 状态码
    private Integer code;
    // 消息内容
    private String msg;
    // 时间戮
    private Long timestamp;
    // 数据内容
    private T data;

    public ServiceResult() {
    }

    // 返回处理具体结果(成功或失败)
    public ServiceResult(ErrorCode errorCode) {
        this.code = (errorCode != null
                && errorCode.getCode() != null
                && !StringUtilsX.isBlank(errorCode.getCode().toString())
        ) ? errorCode.getCode() : ErrorCode.FAIL.getCode();

        this.msg = (errorCode != null
                && errorCode.getMsg() != null
                && !StringUtilsX.isBlank(errorCode.getMsg())
        ) ? errorCode.getMsg() : ErrorCode.FAIL.getMsg();

        this.timestamp = System.currentTimeMillis();

        this.data = null;
    }

    // 返回处理具体结果(成功或失败)
    public ServiceResult(ErrorCode errorCode, T data) {
        this.code = (errorCode != null
                && errorCode.getCode() != null
                && !StringUtilsX.isBlank(errorCode.getCode().toString())
        ) ? errorCode.getCode() : ErrorCode.FAIL.getCode();

        this.msg = (errorCode != null
                && errorCode.getMsg() != null
                && !StringUtilsX.isBlank(errorCode.getMsg())
        ) ? errorCode.getMsg() : ErrorCode.FAIL.getMsg();

        this.timestamp = System.currentTimeMillis();

        this.data = data;
    }

    // 返回处理具体结果(成功或失败)
    public ServiceResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.timestamp = System.currentTimeMillis();
        this.data = null;
    }

    // 返回处理具体结果(成功或失败)
    public ServiceResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.timestamp = System.currentTimeMillis();
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
        return "[" + this.code + "]" + this.msg;
    }

    public String toLongString() {
        return "{"
                + "code:" + this.code + ","
                + "msg:'" + this.msg + "',"
                + "timestamp:" + this.timestamp + ","
                + "data:'" + String.valueOf(this.data) + "'"
                + "}";
    }
}