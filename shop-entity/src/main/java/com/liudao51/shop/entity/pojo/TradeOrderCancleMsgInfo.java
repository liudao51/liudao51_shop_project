package com.liudao51.shop.entity.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单取消实体
 */
@Data
public class TradeOrderCancleMsgInfo implements Serializable {
    private String topic;
    private String tag;
    private String key;
    private String body;
}
