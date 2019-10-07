package com.qf.web.filter;

import com.qf.domain.User;
import com.qf.service.UserService;
import com.qf.service.impl.UserServiceImpl;
import com.qf.utils.Base64Utils;
import org.springframework.web.context.ContextLoader;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AutoLoginFilter", value = "/index.jsp")
public class AutoLoginFilter implements Filter {
    private UserService userService = (UserService) ContextLoader.getCurrentWebApplicationContext().getBean("userServiceImpl");

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //1.判断是否已经登录（查看session中是否有用户）
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //转换成User！！！
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            chain.doFilter(req, resp);
            return;
        }
        //2.没有登录，
        //cookie可以理解为map集合
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userinfo")) {
                    //获取存入cookie的  用户名#密码
                    String value = cookie.getValue();
                    //解码
                    String userinfo = Base64Utils.decode(value);
                    String[] userinfos = userinfo.split("#");
                    //UserService userService = new UserServiceImpl();
                    //在过滤器中登录
                    User user1 = userService.login(userinfos[0], userinfos[1]);
                    if (user1 != null) {
                        if (user1.getFlag() == 1) {
                            //登录过后，设置session，刷新最后一次登录时间
                            request.getSession().setAttribute("user", user1);
                            chain.doFilter(req, resp);
                            return;
                        }
                    } else {
                        //登录失败，可能cookie已经被修改，所以删除
                        Cookie cookie1 = new Cookie("userinfo", "");
                        cookie1.setMaxAge(0);
                        cookie1.setPath("/");
                        response.addCookie(cookie1);
                    }
                }
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
