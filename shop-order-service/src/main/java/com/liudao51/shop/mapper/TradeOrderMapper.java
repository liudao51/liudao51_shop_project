package com.liudao51.shop.mapper;

import com.liudao51.shop.entity.po.TradeOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 订单 Mapper 接口
 */
@Mapper
@Repository
public interface TradeOrderMapper extends BaseMapper<TradeOrder> {

}