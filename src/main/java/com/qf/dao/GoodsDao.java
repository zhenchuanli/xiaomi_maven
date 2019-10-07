package com.qf.dao;

import com.qf.domain.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Oldman 2019/9/11 11:33
 * bug? 不存在的
 */
public interface GoodsDao {
    long getCount(@Param("condition") String condition);

    List<Goods> findPageByWhere(@Param("pageNum") int pageNum,@Param("pageSize") int pageSize,@Param("condition") String condition);

    Goods findById(@Param("gid")int gid);
}
