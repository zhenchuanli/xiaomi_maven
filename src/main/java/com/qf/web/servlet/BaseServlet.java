package com.qf.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Oldman 2019/9/10 15:00
 * bug? 不存在的
 */
//@WebServlet(name = "BaseServlet")  不要配地址
public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //判断用户行为，传入的参数
        String methodName = request.getParameter("method");
        //利用反射调用
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            String url = (String) method.invoke(this, request, response);  //this.method
            //需要转发或重定向，执行
            if (url != null && url.trim().length() != 0) {
                //转发
                if (url.startsWith("redirect:")) {
                    response.sendRedirect(request.getContextPath() + url.split(":")[1]);
                } else {
                    request.getRequestDispatcher(url).forward(request, response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
