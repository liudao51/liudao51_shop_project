package com.liudao51.shop.common.constant;

/**
 * 错误码列表
 */
public enum ErrorCode {

    /*
     * 格式为："功能_具体错误_ERROR", 如：REQUEST_SIGN_ERROR, USER_NOT_AUTHORIZE_ERROR
     *
     * 长度为：大于5位整数。
     * 0 - 成功,
     * 1 - 失败,
     * 1xxxx - 系统相关,
     * 20xxx - 请求相关,
     * 21xxx - 服务相关,
     * 22xxx - 用户相关,
     * 23xxx - 商品相关,
     * 24xxx - 订单相关,
     * 25xxx - 优惠券相关,
     * 26xxx - 支付券相关,
     */
    //系统错误
    SYSTEM_UNKNOWN_ERROR(10001, "UNKNOWN_ERROR", "未知错误"),
    SYSTEM_TIMEOUT_ERROR(10002, "TIMEOUT_ERROR", "系统繁忙"),

    //IO错误
    IO_RESOURCES_ERROR(11001, "RESOURCES_ERROR", "资源文件读取错误"),
    IO_FILE_FORMAT_ERROR(11001, "FILE_FORMAT_ERROR", "文件格式错误"),

    //消息错误
    MQ_MESSAGE_SEND_ERROR(11201, "MQ_MESSAGE_SEND_ERROR", "MQ消息发送错误"),
    MQ_CONSUMER_STATUS_INSERT_ERROR(11202, "MQ_CONSUMER_STATUS_INSERT_ERROR", "消费者消息记录插入错误"),
    MQ_CONSUMER_STATUS_PROCESSING_UPDATE_ERROR(11203, "MQ_CONSUMER_STATUS_PROCESSING_UPDATE_ERROR", "消费者消息状态处理中更新错误"),
    MQ_CONSUMER_STATUS_SUCCESS_UPDATE_ERROR(11204, "MQ_CONSUMER_STATUS_SUCCESS_UPDATE_ERROR", "消费者消息状态成功更新错误"),
    MQ_CONSUMER_STATUS_FAIL_UPDATE_ERROR(11205, "MQ_CONSUMER_STATUS_FAIL_UPDATE_ERROR", "消费者消息状态失败更新错误"),
    MQ_MESSAGE_GOODS_INVALID_ERROR(11206, "MQ_MESSAGE_GOODS_INVALID_ERROR", "消息中的商品无效错误"),

    //请求错误
    REQUEST_SIGN_ERROR(20001, "SIGN_ERROR", "签名错误"),
    REQUEST_SIGN_TYPE_ERROR(20002, "SIGN_TYPE_ERROR", "签名类型错误"),
    REQUEST_APP_ID_ERROR(20003, "APP_ID_ERROR", "APP_ID错误"),
    REQUEST_APP_SECRET_ERROR(20004, "APP_SECRET_ERROR", "APP_SECRET错误"),
    REQUEST_PARAM_ERROR(20005, "PARAM_ERROR", "请求参数错误"),
    REQUEST_NEED_IMPLEMENTS_PARAMVALIDINTERFACE_ERROR(20006, "NEED_IMPLEMENTS_PARAMVALIDINTERFACE_ERROR", "请求参数对象必须实现要PARAMVALIDINTERFACE接口"),
    REQUEST_PAGE_ERROR(20007, "PAGE_ERROR", "分页错误"),

    //用户错误
    USER_QUERY_ERROR(22001, "USER_QUERY_ERROR", "用户查询失败"),
    USER_NOT_EXITS_ERROR(22002, "USER_NOT_EXITS_ERROR", "用户不存在"),
    USER_ALREADY_EXITS_ERROR(23003, "USER_ALREADY_EXITS_ERROR", "用户已存在"),
    USER_USERNAME_PASSWORD_ERROR(22004, "USER_USERNAME_PASSWORD_ERROR", "用户名或密码错误"),
    USER_PASSWORD_ERROR(22005, "USER_PASSWORD_ERROR", "密码错误"),
    USER_NAME_FORMAT_ERROR(22006, "USER_NAME_FORMAT_ERROR", "用户名格式错误"),
    USER_PASSWORD_FORMAT_ERROR(22007, "USER_PASSWORD_FORMAT_ERROR", "用户密码格式错误"),
    USER_UNACTIVATED_ERROR(22008, "USER_UNACTIVATED_ERROR", "用户未激活"),
    USER_FORBIDDEN_ERROR(22009, "USER_FORBIDDEN_ERROR", "用户已禁用"),
    USER_FREEZE_ERROR(22010, "USER_FREEZE_ERROR", "用户已冻结"),
    USER_NOT_AUTHORIZE_ERROR(22011, "USER_NOT_AUTHORIZE_ERROR", "没有此操作权限"),
    USER_NOT_LOGIN_ERROR(22012, "USER_NOT_LOGIN_ERROR", "请先登录"),
    USER_ADD_ERROR(22013, "USER_ADD_ERROR", "用户新增失败"),
    USER_UPDATE_ERROR(22014, "USER_UPDATE_ERROR", "用户更新失败"),
    USER_DELETE_ERROR(22015, "USER_DELETE_ERROR", "用户删除失败"),

    //商品错误
    GOODS_QUERY_ERROR(23001, "GOODS_QUERY_ERROR", "商品查询失败"),
    GOODS_INVALID_ERROR(23002, "GOODS_INVALID_ERROR", "商品无效"),
    GOODS_NOT_EXITS_ERROR(23003, "GOODS_NOT_EXITS_ERROR", "商品不存在"),
    GOODS_PRICE_INVALID_ERROR(23004, "GOODS_PRICE_INVALID_ERROR", "商品价格无效"),
    GOODS_STOCK_NOT_ENOUGH_ERROR(23005, "GOODS_STOCK_NOT_ENOUGH_ERROR", "商品库存不足"),
    GOODS_STOCK_REDUCE_ERROR(23006, "GOODS_STOCK_REDUCE_ERROR", "商品库存扣除失败"),
    GOODS_STOCK_LOG_GOODS_NOT_EXITS_ERROR(23107, "GOODS_STOCK_LOG_GOODS_NOT_EXITS_ERROR", "商品库存日志商品不存在"),
    GOODS_STOCK_ROLLBACK_ERROR(23108, "GOODS_STOCK_ROLLBACK_ERROR", "商品库存回退失败"),

    //订单错误
    ORDER_INVALID_ERROR(24001, "ORDER_INVALID_ERROR", "订单无效"),
    ORDER_NOT_EXIST_ERROR(24002, "ORDER_NOT_EXIST_ERROR", "订单不存在"),
    ORDER_ORDER_SN_ERROR(24003, "ORDER_SN_ERROR", "订单号错误"),
    ORDER_CREATE_PRE_ORDER_ERROR(24004, "CREATE_PRE_ORDER_ERROR", "预订单生成失败"),
    ORDER_SHIPPINGFEE_ERROR(24005, "ORDER_SHIPPINGFEE_ERROR", "订单运费不正确"),
    ORDER_AMOUNT_ERROR(24005, "ORDER_AMOUNT_ERROR", "订单金额不正确"),
    ORDER_MONEY_PAID_ERROR(24006, "ORDER_MONEY_PAID_ERROR", "订单使用余额不正确"),
    ORDER_REPEAT_PAY_ERROR(24007, "ORDER_REPEAT_PAY_ERROR", "订单重复支付"),
    ORDER_CONFIRM_ERROR(24008, "ORDER_CONFIRM_ERROR", "订单确认失败"),
    ORDER_REDUCE_DISCOUNT_ERROR(24009, "ORDER_REDUCE_DISCOUNT_ERROR", "订单优惠扣减失败"),
    ORDER_ORDER_STATUS_CANCEL_ERROR(24010, "ORDER_STATUS_CANCEL_ERROR", "订单状态取消失败"),

    //优惠券错误
    COUPON_QUERY_ERROR(25001, "COUPON_QUERY_ERROR", "优惠券查询失败"),
    COUPON_INVALID_ERROR(25002, "COUPON_INVALID_ERROR", "优惠券无效"),
    COUPON_NOT_EXIST_ERROR(25003, "COUPON_NOT_EXIST_ERROR", "优惠券不存在"),
    COUPON_IS_USED_ERROR(25004, "COUPON_IS_USED_ERROR", "优惠券已使用"),
    COUPON_MONEY_ERROR(25005, "COUPON_MONEY_ERROR", "优惠券金额不正确"),
    COUPON_REDUCE_ERROR(25005, "COUPON_REDUCE_ERROR", "优惠券扣减失败"),
    COUPON_ORDER_ID_ERROR(25006, "COUPON_ORDER_ID_ERROR", "使用优惠券的订单ID不正确"),
    COUPON_USE_STATUS_CANCEL_ERROR(25007, "COUPON_USE_STATUS_CANCEL_ERROR", "优惠券使用状态取消失败"),


    //成功失败状态
    SUCCESS(0, "SUCCESS", "成功"),
    FAIL(1, "FAIL", "失败");

    private Integer code;
    private String value;
    private String msg;

    ErrorCode(Integer code, String value, String msg) {
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
