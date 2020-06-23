package com.liudao51.shop.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 客户端商品实体类
 */
@Data
public class TradeGoodsVo {

    private Long goodsId;

    private String goodsName;

    private Integer goodsStock;

    private BigDecimal goodsPrice;

    private String goodsDesc;
}
