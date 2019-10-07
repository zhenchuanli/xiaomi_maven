package com.qf.dao;

import com.qf.domain.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Oldman 2019/9/11 21:31
 * bug? 不存在的
 */
public interface CartDao {
    Cart findByUidAndPid(@Param("uid") int uid,@Param("pid") int pid);

    void add(Cart cart);

    void update(Cart cart);

    List<Cart> findByUid(@Param("uid") int uid);

    void delete(@Param("uid") int uid,@Param("pid") int pid);

    void deleteByUid(@Param("uid") int uid);
}
