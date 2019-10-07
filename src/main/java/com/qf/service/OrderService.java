package com.qf.service;

import com.qf.domain.Order;
import com.qf.domain.OrderDetail;

import java.util.List;

/**
 * Oldman 2019/9/12 10:24
 * bug? 不存在的
 */
public interface OrderService {
    void saveOrder(Order order, List<OrderDetail> orderDetails);

    List<Order> findAllByUserId(int uid);
}
