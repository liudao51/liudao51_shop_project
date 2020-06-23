package com.liudao51.shop.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数据库表商品实体类
 */
@Data
public class TradeGoods implements Serializable {
    private Long goodsId;

    private String goodsName;

    private Integer goodsStock;

    private BigDecimal goodsPrice;

    private String goodsDesc;

    private Long createTime;

    private Long updateTime;
}