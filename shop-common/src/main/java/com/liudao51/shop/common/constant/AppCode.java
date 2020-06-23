package com.liudao51.shop.common.constant;

/**
 * 应用代码列表
 */
public enum AppCode {
    //算法加密
    SIGNTYPE_NONE(0, "NONE", "不签名"),
    SIGNTYPE_MD5(1, "MD5", "MD5计算摘要"),
    SIGNTYPE_HMAC_MD5(2, "HMAC_MD5", "HMAC_MD5计算摘要"),
    SIGNTYPE_HMAC_SHA256(3, "HMAC_SHA256", "HMAC_SHA256计算摘要"),
    SIGNTYPE_AES(4, "AES", "AES对称加密"),
    SIGNTYPE_RSA(5, "RSA", "RSA公钥私钥非对称加密算法"),
    SIGNTYPE_RSA_SHA256(6, "RSA_SHA256", "RSA_SHA256(RS2)公钥私钥非对称加密算法"),

    //请求格式
    REQUEST_FORMAT_JSON(1, "json", "json格式"),
    REQUEST_FORMAT_XML(2, "xml", "xml格式"),

    //字符编码
    CHARSET_UTF8(1, "utf-8", "utf-8格式"),
    CHARSET_GBK(2, "gbk", "gbk格式"),
    CHARSET_GB2312(3, "gb2312", "gb2312格式"),

    //订单状态
    ORDER_ORDER_STATUS_NO_CONFIRM(0, "NO_CONFIRM", "订单未确认"),
    ORDER_ORDER_STATUS_ALREADY_CONFIRM(1, "ALREADY_CONFIRM", "订单已确认"),
    ORDER_ORDER_STATUS_CANCEL(2, "CANCEL", "订单已取消"),
    ORDER_ORDER_STATUS_INVALID(3, "INVALID", "订单无效"),
    ORDER_ORDER_STATUS_REFUND(4, "REFUND", "订单退款"),

    //订单付款状态
    ORDER_PAY_STATUS_NO_PAY(0, "NO_PAY", "订单未支付"),
    ORDER_PAY_STATUS_IN_PAY(1, "IN_PAY", "订单支付中"),
    ORDER_PAY_STATUS_ALREADY_PAY(2, "ALREADY_PAY", "订单已支付"),

    //订单发货状态
    ORDER_SHIPPING_STATUS_NO_DELIVER(0, "NO_DELIVER", "订单未发货"),
    ORDER_SHIPPING_STATUS_ALREADY_DELIVER(1, "ALREADY_DELIVER", "订单已发货"),
    ORDER_SHIPPING_STATUS_ALREADY_RECEIVING(2, "ALREADY_RECEIVING", "订单已收货"),

    //优惠券使用状态
    COUPON_USE_STATUS_NO_USE(0, "NO_USE", "优惠券未使用"),
    COUPON_USE_STATUS_ALREADY_USE(1, "ALREADY_USE", "优惠券已使用"),

    //消息处理状态
    MQ_CONSUMER_STATUS_PROCESSING(0, "PROCESSING", "正在处理"),
    MQ_CONSUMER_STATUS_SUCCESS(1, "SUCCESS", "处理成功"),
    MQ_CONSUMER_STATUS_FAIL(2, "FAIL", "处理失败"),
    MQ_CONSUMER_COUNT_MAX(3, "CONSUMER_COUNT_MAX", "允许最大的失败次数"),

    //MQ消息(作为默认值,最好与application.properties文件中的app.mq.xxx值保持一致)
    MQ_TOPIC_ORDER(0, "TOPIC_ORDER", "主题为订单"),
    MQ_TAG_ORDER_CANCEL(0, "TAG_ORDER_CANCEL", "标签为订单取消"),
    MQ_CONSUMER_GROUP_ORDER(0, "CONSUMER_GROUP_ORDER", "消费者组为订单"),

    //分页信息
    PAGE_PAGE_NO(1, "page_no", "默认当前页"),
    PAGE_PAGE_SIZE(10, "page_size", "默认页码大小"),


    //成功失败状态
    SUCCESS(0, "SUCCESS", "成功"),
    FAIL(1, "FAIL", "失败");

    private Integer code;
    private String value;
    private String msg;

    AppCode(Integer code, String value, String msg) {
        this.code = code;
        this.value = value;
        this.msg = msg;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }

    public String getMsg() {
        return this.msg;
    }

    @Override
    public String toString() {
        return "[" + this.code + "]" + this.msg;
    }

    public String toLongString() {
        return "{"
                + "code:" + this.code + ","
                + "value:'" + this.value + "',"
                + "msg:'" + this.msg + "'"
                + "}";
    }
}
