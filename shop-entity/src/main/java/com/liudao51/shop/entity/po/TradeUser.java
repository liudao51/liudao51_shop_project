package com.liudao51.shop.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TradeUser implements Serializable {
    private Long userId;

    private String userName;

    private String userPassword;

    private String userMobile;

    private Integer userScore;

    private BigDecimal userMoney;

    private Long createTime;

    private Long updateTime;
}