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
     * 21xxx - 用户相关,
     * 22xxx - 订单相关,
     * 23xxx - 商品券相关,
     * 24xxx - 支付券相关,
     * 25xxx - 优惠券相关,
     * 30xxx - IO读写相关,
     */
    SYSTEM_UNKNOWN_ERROR(10001, "未知错误"),
    SYSTEM_TIMEOUT_ERROR(10002, "系统繁忙"),
    SYSTEM_RESOURCES_IO_ERROR(10003, "资源文件读取错误"),

    REQUEST_SIGN_ERROR(20001, "签名错误"),
    REQUEST_SIGN_TYPE_ERROR(20002, "签名类型错误"),
    REQUEST_APP_ID_ERROR(20003, "APP_ID错误"),
    REQUEST_APP_SECRET_ERROR(20004, "APP_SECRET错误"),
    REQUEST_PARAM_ERROR(20005, "请求参数错误"),
    REQUEST_NEED_IMPLEMENTS_PARAMVALIDINTERFACE_ERROR(20006, "请求参数对象必须实现要ParamValidInterface接口"),
    REQUEST_PAGE_NO_OR_PAGE_SIZE_ERROR(20007, "页码或页数错误"),

    USER_NOT_AUTHORIZE_ERROR(21001, "没有此操作权限"),
    USER_NOT_LOGIN_ERROR(21002, "请先登录"),
    USER_ALREADY_EXITS_ERROR(21003, "用户已存在"),
    USER_NOT_EXITS_ERROR(21004, "用户不存在"),
    USER_USERNAME_PASSWORD_ERROR(21005, "用户名或密码错误"),
    USER_PASSWORD_ERROR(21006, "密码错误"),
    USER_NAME_FORMAT_ERROR(21007, "用户名格式错误"),
    USER_PASSWORD_FORMAT_ERROR(21008, "密码格式错误"),
    USER_UNACTIVATED_ERROR(21009, "用户未激活"),
    USER_FORBIDDEN_ERROR(21010, "用户已禁用"),
    USER_FREEZE_ERROR(21011, "用户已冻结"),
    USER_ADD_ERROR(21012, "用户新增失败"),
    USER_UPDATE_ERROR(21013, "用户更新失败"),
    USER_DELETE_ERROR(21014, "用户删除失败"),

    ORDER_ORDER_SN_ERROR(22001, "订单号错误"),
    ORDER_REPEAT_PAY_ERROR(22002, "订单重复支付"),

    STREAM_FILE_FORMAT_ERROR(30001, "文件格式错误"),

    FAIL(1, "fail"),
    SUCCESS(0, "success");

    private Integer code;
    private String message;

    ErrorCode(Integer code, String message) {
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
