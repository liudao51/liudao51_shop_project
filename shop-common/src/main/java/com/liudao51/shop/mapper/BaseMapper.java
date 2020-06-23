package com.liudao51.shop.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {

    Integer insertById(@Param("parameter") T entity);

    Integer deleteById(@Param("parameter") T entity);

    Integer delete(@Param("parameter") Map<String, Object> args);

    Integer updateById(@Param("parameter") T entity);

    Integer update(@Param("parameter") Map<String, Object> args);

    T selectById(@Param("parameter") T entity);

    T selectOne(@Param("parameter") Map<String, Object> args);

    List<T> selectList(@Param("parameter") Map<String, Object> args);

    Integer selectCount(@Param("parameter") Map<String, Object> args);
}