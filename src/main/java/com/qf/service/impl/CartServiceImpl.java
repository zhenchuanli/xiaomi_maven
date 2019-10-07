package com.qf.service.impl;

import com.qf.dao.CartDao;
import com.qf.dao.GoodsDao;
import com.qf.domain.Cart;
import com.qf.service.CartService;
import com.qf.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Oldman 2019/9/11 21:23
 * bug? 不存在的
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private GoodsDao goodsDao;
    @Override
    public Cart findByUidAndPid(int uid, int pid) {
        return cartDao.findByUidAndPid(uid, pid);
    }

    @Override
    public void add(Cart cart) {
        cartDao.add(cart);
    }

    @Override
    public void update(Cart cart) {
        cartDao.update(cart);
    }

    @Override
    public List<Cart> findByUid(int id) {
        List<Cart> carts = cartDao.findByUid(id);
        //GoodsService goodsService = new GoodsServiceImpl();
        if (carts != null) {
            for (Cart cart : carts) {
                //根据商品id查询商品
                cart.setGoods(goodsDao.findById(cart.getPid()));
            }
        }
        return carts;
    }

    @Override
    public void delete(int uid, int pid) {
        cartDao.delete(uid, pid);
    }

    @Override
    public void deleteByUid(int uid) {
        cartDao.deleteByUid(uid);
    }


}
