package com.liudao51.shop.entity.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class TradeGoodsStockLog implements Serializable {

    private Long goodsStockId;

    private Long goodsId;

    private Long orderId;

    private Integer goodsStock;

    private Long createTime;

    private Long updateTime;
}