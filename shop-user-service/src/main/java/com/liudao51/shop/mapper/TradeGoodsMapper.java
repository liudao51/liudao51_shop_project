package com.liudao51.shop.mapper;

import com.liudao51.shop.entity.po.TradeUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户 Mapper 接口
 */
@Mapper
@Repository
public interface TradeGoodsMapper {

    TradeUser selectById(@Param("parameter") Map<String, Object> args);

    TradeUser selectOne(@Param("parameter") Map<String, Object> args);

    List<TradeUser> selectList(@Param("parameter") Map<String, Object> args);
}