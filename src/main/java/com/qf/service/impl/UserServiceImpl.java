package com.qf.service.impl;

import com.qf.dao.UserDao;
import com.qf.domain.User;
import com.qf.service.UserService;
import com.qf.utils.EmailUtils;
import com.qf.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Oldman 2019/9/10 15:43
 * bug? 不存在的
 *
 * 处理业务逻辑
 */

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    //注册用户
    @Override
    public void register(User user) {

        //密码加密存储
        user.setPassword(MD5Utils.md5(user.getPassword()));
        //注册（往数据库添加用户）
        userDao.add(user);
        //发送邮件
        EmailUtils.sendEmail(user);
    }

    @Override
    public User checkUserName(String username) {

        return userDao.findByUserName(username);
    }

    @Override
    public User login(String username, String password) {
        //把密码加密后在比对 MD5加密不可逆
        //因为数据库中存入的是加密后的密码，所以查询的时候也需要先加密
        password = MD5Utils.md5(password);

        return userDao.findByUserNameAndPassword(username, password);
    }
}
