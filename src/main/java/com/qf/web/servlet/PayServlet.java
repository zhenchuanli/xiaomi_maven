package com.qf.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Oldman 2019/9/12 15:48
 * bug? 不存在的
 */
@WebServlet(name = "PayServlet", value = "/payservlet")
public class PayServlet extends BaseServlet {
    public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //订单号
        String orderid = request.getParameter("orderid");
        //银行
        String pd_frpId = request.getParameter("pd_FrpId");
        //支付金额
        String p3_Amt = "0.01";

        String url ="https://www.yeepay.com/app-merchant-proxy/node?拼13个参数";
        response.sendRedirect(url);

        return url;
    }


}
