package com.qf.test;

import com.qf.domain.User;
import com.qf.service.UserService;
import com.qf.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Oldman 2019/10/6 2:41
 * bug? 不存在的
 */
public class TestUserService {
    //获取工厂
    ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    UserService userService = context.getBean("userServiceImpl", UserServiceImpl.class);

    @Test
    public void testLogin() {
        User user = userService.login("张三", "123456");
        System.out.println("user = " + user);
    }
}
