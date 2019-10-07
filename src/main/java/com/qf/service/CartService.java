package com.qf.service;

import com.qf.domain.Cart;

import java.util.List;

/**
 * Oldman 2019/9/11 21:23
 * bug? 不存在的
 */
public interface CartService {
    Cart findByUidAndPid(int uid, int pid);

    void add(Cart cart);

    void update(Cart cart);


    List<Cart> findByUid(int id);

    void delete(int uid, int pid);

    void deleteByUid(int uid);
}
