package com.liudao51.shop.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TradePay implements Serializable {
    private Long payId;

    private Long orderId;

    private BigDecimal payAmount;

    private Integer isPaid;

    private Long createTime;

    private Long updateTime;
}