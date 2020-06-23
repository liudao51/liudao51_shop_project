package com.liudao51.shop.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TradeCoupon implements Serializable {
    private Long couponId;

    private BigDecimal couponPrice;

    private Long userId;

    private Long orderId;

    private Integer useStatus;

    private Long useTime;

    private Long createTime;

    private Long updateTime;
}