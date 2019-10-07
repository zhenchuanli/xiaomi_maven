package com.qf.dao;

import com.qf.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Oldman 2019/9/10 15:30
 * bug? 不存在的
 */
public interface UserDao {

    List<User> findAll();

    User findById(@Param("id") Integer id);

    User findByUserNameAndPassword(@Param("username") String username, @Param("password") String password);

    User findByUserName(@Param("username") String username);

    void add(User user);

    void update(User user);

    void delete(@Param("id") Integer id);
}
