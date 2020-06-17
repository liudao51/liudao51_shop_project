package com.liudao51.shop.mapper;

import com.liudao51.shop.entity.po.TradeOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 订单 Mapper 接口
 */
@Mapper
@Repository
public interface TradeOrderMapper {

    TradeOrder selectById(@Param("parameter") Map<String, Object> args);

    TradeOrder selectOne(@Param("parameter") Map<String, Object> args);

    List<TradeOrder> selectList(@Param("parameter") Map<String, Object> args);

    int insert(TradeOrder order);
}