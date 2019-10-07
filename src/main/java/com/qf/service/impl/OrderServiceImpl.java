package com.qf.service.impl;

import com.qf.dao.CartDao;
import com.qf.dao.OrderDao;
import com.qf.dao.OrderDetailDao;
import com.qf.domain.Order;
import com.qf.domain.OrderDetail;
import com.qf.service.OrderService;
import com.qf.utils.DataSourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.sql.SQLException;
import java.util.List;

/**
 * Oldman 2019/9/12 10:24
 * bug? 不存在的
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderDetailDao orderDetailDao;
    @Autowired
    CartDao cartDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrder(Order order, List<OrderDetail> orderDetails) {

        //try {
            //1.开事务
            //DataSourceUtils.startTransaction();
            //2.调用dao向order中添加订单
            //OrderDao orderDao = new OrderDaoImpl();
            orderDao.add(order);
            //3.向订单详情表添加
            //OrderDetailDao orderDetailDao = new OrderDetailDaoImpl();
            for (OrderDetail orderDetail : orderDetails) {
                orderDetailDao.add(orderDetail);
            }
            //4.清空购物车
            //CartDao cartDao = new CartDaoImpl();
            cartDao.deleteByUid(order.getUid());
            //5.提交
            //DataSourceUtils.commit();


        /*} catch (Exception e) {
            e.printStackTrace();
            try {
                DataSourceUtils.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                DataSourceUtils.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
    }

    @Override
    public List<Order> findAllByUserId(int uid) {
        //OrderDao orderDao = new OrderDaoImpl();
        return orderDao.findAllByUserId(uid);
    }
}
