package com.qf.dao;

import com.qf.domain.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Oldman 2019/9/12 14:58
 * bug? 不存在的
 */
public interface OrderDao {

    void add(Order order);

    List<Order> findAllByUserId(@Param("uid") int uid);
}
