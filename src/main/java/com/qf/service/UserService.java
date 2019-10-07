package com.qf.service;

import com.qf.domain.User;

/**
 * Oldman 2019/9/10 15:41
 * bug? 不存在的
 */
public interface UserService {

    //应用场景
    void register(User user);
    //检查用户名是否存在
    User checkUserName(String username);

    User login(String username, String password);
}
