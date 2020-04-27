package com.liudao51.shop.common.constant;

import java.io.Serializable;

public class RequestConstant {
    /**
     * TODO: Api计算签名算法类型
     *
     * @option 可选值：NONE, MD5, HMAC_MD5, HMAC_SHA256, AES, RSA, RSA_SHA256
     */
    public final static class SignType {
        public final static String NONE = "NONE"; //不签名
        public final static String MD5 = "MD5"; //md5计算摘要
        public final static String HMAC_MD5 = "HMAC_MD5"; //hmac_md5计算摘要
        public final static String HMAC_SHA256 = "HMAC_SHA256"; //hmac_sha256计算摘要
        public final static String AES = "AES"; //aes对称加密
        public final static String RSA = "RSA"; //rsa公钥私钥非对称加密算法
        public final static String RSA_SHA256 = "RSA_SHA256"; //rsa_sha256(rs2)公钥私钥非对称加密算法
    }

    /**
     * TODO: Api响应格式
     *
     * @option 可选值：json, xml
     */
    public final static class Format {
        public final static String JSON = "json"; //json格式
        public final static String XML = "xml"; //xml格式
    }

    /**
     * TODO: Api字符编码
     *
     * @option 可选值：UTF8, GBK, GB2312
     */
    public final static class Charset {
        public final static String UTF8 = "utf-8"; //utf-8
        public final static String GBK = "gbk"; //gbk
        public final static String GB2312 = "gb2312"; //gb2312
    }

    /**
     * TODO: Api响应状态status
     *
     * @option 可选值： SUCCESS, FAIL
     */
    public final static class Status {
        public final static String SUCCESS = "success"; //成功
        public final static String FAIL = "fail"; //失败
    }

    /**
     * TODO: 分页信息
     */
    public final static class PAGE {
        public final static Long PAGE_NO = 1L; //默认当前页
        public final static Long PAGE_SIZE = 10L; //默认页码大小
    }
}