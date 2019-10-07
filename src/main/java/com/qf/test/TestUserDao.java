package com.qf.test;

import com.qf.dao.UserDao;
import com.qf.domain.User;
import com.qf.utils.MD5Utils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.security.provider.MD5;

import java.util.List;

/**
 * Oldman 2019/9/26 22:38
 * bug? 不存在的
 *
 * List<User> findAll();
 * User findById(Integer id);
 * User findByUserNameAndPassword(@Param("username") String username, @Param("password") String password);
 * User findByUserName(String username);
 * void add(User user);
 * void update(User user);
 * void delete(Integer id);
 */
public class TestUserDao {
    //获取工厂
    ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    UserDao userDao = (UserDao) context.getBean("userDao");


    @Test
    public void findAll() {
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void findById() {
        User user = userDao.findById(12);
        System.out.println(user);
    }

    @Test
    public void findByUserNameAndPassword() {
        String password = MD5Utils.md5("123456");
        User user = userDao.findByUserNameAndPassword("张三", password);
        System.out.println(user);
    }

}
