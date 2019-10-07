package com.qf.dao;

import com.qf.domain.GoodsType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Oldman 2019/9/11 10:38
 * bug? 不存在的
 */
public interface GoodsTypeDao {

    List<GoodsType> findByLevel(@Param("level") int level);

    GoodsType findById(@Param("typeid") int typeid);
}
