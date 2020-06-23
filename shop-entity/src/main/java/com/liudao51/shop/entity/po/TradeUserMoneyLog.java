package com.liudao51.shop.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TradeUserMoneyLog implements Serializable {
    private Long userMoneyLogId;

    private Long userId;

    private Long orderId;

    private Integer moneyLogType;

    private BigDecimal useMoney;

    private Long createTime;

    private Long updateTime;
}