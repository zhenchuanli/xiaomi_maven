package com.qf.dao;

import com.qf.domain.OrderDetail;
import org.apache.ibatis.annotations.Param;

/**
 * Oldman 2019/9/12 14:59
 * bug? 不存在的
 */
public interface OrderDetailDao {

    void add(@Param("orderDetail") OrderDetail orderDetail);
}
