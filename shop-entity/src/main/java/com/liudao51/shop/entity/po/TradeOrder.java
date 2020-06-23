package com.liudao51.shop.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TradeOrder implements Serializable {
    private Long orderId;

    private Long userId;

    private Integer orderStatus;

    private Integer payStatus;

    private Integer shippingStatus;

    private String address;

    private String consignee;

    private Long goodsId;

    private Integer goodsNumber;

    private BigDecimal goodsPrice;

    private BigDecimal goodsAmount;

    private BigDecimal shippingFee;

    private BigDecimal orderAmount;

    private Long couponId;

    private BigDecimal couponPaid;

    private BigDecimal moneyPaid;

    private BigDecimal payAmount;

    private Long payTime;

    private Long confirmTime;

    private Long createTime;

    private Long updateTime;
}