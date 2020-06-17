package com.liudao51.shop.common.constant;

/**
 * 应用代码列表
 */
public enum AppCode {

    ORDER_NO_CONFIRM(0, "订单未确认"),

    APP_SUCCESS(1,"成功"),
    APP_FAIL(0,"失败");

    private Integer code;
    private String message;

    AppCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    @Override
    public String toString() {
        return "ErrorCode={"
                + "code:" + this.code
                + ", message:'" + this.message
                + "}";
    }
}
