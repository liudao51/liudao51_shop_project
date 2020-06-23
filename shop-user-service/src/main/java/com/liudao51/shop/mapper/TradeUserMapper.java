package com.liudao51.shop.mapper;

import com.liudao51.shop.entity.po.TradeUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 用户 Mapper 接口
 */
@Mapper
@Repository
public interface TradeUserMapper extends BaseMapper<TradeUser> {
}