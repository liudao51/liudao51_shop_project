package com.liudao51.shop.common.exception;

import com.liudao51.shop.common.constant.ErrorCode;

/**
 * 自定义程序异常
 */
public class AppException extends RuntimeException {

    // 状态码
    private Integer code;
    // 消息内容
    private String msg;

    public AppException(ErrorCode errorCode) {
        super("[" + errorCode.getCode() + "]" + errorCode.getMsg());
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
    }

    public AppException(Integer code, String msg) {
        super("[" + code + "]" + msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "[" + this.code + "]" + this.msg;
    }

    public String toLongString() {
        return "{"
                + "code:" + this.code + ","
                + "msg:'" + this.msg + "'"
                + "}";
    }
}
