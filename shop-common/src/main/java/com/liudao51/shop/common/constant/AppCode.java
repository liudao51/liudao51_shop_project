package com.liudao51.shop.common.constant;

/**
 * 应用代码列表
 */
public enum AppCode {

    ORDER_NO_CONFIRM(0, "订单未确认"),

    COUPON_IS_USED(1, "优惠券已经使用"),
    COUPON_IS_UNUSED(0, "优惠券未使用"),

    APP_SUCCESS(0, "成功"),
    APP_FAIL(1, "失败");

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
