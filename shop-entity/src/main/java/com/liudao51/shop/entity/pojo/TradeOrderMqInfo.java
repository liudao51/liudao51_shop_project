package com.liudao51.shop.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单Mq实体类
 */
@Data
public class TradeOrderMqInfo implements Serializable {

    private Long orderId;
    private Long couponId;
    private Long userId;
    private BigDecimal userMoney;
    private Long goodsId;
    private Integer goodsNum;
}
