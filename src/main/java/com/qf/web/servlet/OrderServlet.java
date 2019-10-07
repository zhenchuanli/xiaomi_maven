package com.qf.web.servlet;

import com.qf.domain.*;
import com.qf.service.AddressService;
import com.qf.service.CartService;
import com.qf.service.OrderService;
import com.qf.service.impl.AddressServiceImpl;
import com.qf.service.impl.CartServiceImpl;
import com.qf.service.impl.OrderServiceImpl;
import com.qf.utils.RandomUtils;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Oldman 2019/9/12 10:17
 * bug? 不存在的
 */
@WebServlet(name = "OrderServlet", value = "/orderservlet")
public class OrderServlet extends BaseServlet {

    private CartService cartService = (CartService) ContextLoader.getCurrentWebApplicationContext().getBean("cartServiceImpl");
    private AddressService addressService = (AddressService) ContextLoader.getCurrentWebApplicationContext().getBean("addressServiceImpl");
    private OrderService orderService = (OrderService) ContextLoader.getCurrentWebApplicationContext().getBean("orderServiceImpl");

    //展示
    public String getOrderView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }

        //OrderService orderService = new OrderServiceImpl();

        //查询购物的商品，在订单预览界面展示物品
        //CartService cartService = new CartServiceImpl();
        List<Cart> carts = cartService.findByUid(user.getId());
        request.setAttribute("carts", carts);

        //获取收到货地址
        //AddressService addressService = new AddressServiceImpl();
        //根据用户的id查询到用户的收货地址
        List<Address> addList = addressService.findByUid(user.getId());
        //把查到的集合放入域
        request.getSession().setAttribute("addList", addList);


        return "/order.jsp";
    }

    //提交订单【重点】  事务
    public String addOrder(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        //获取参数
        String aid = request.getParameter("aid");
        //1.获取购买的物品
        //CartService cartService = new CartServiceImpl();
        List<Cart> carts = cartService.findByUid(user.getId());

        if (carts == null || carts.size() == 0) {
            request.setAttribute("msg", "购物车为空");
            return "/message.jsp";
        }

        //创建订单号
        String oid = RandomUtils.createOrderId();
        //遍历
        List<OrderDetail> orderDetails = new ArrayList<>();
        BigDecimal sum = new BigDecimal(0);
        for (Cart cart : carts) {
            //2.订单详情
            OrderDetail orderDetail = new OrderDetail(null, oid, cart.getPid(), cart.getNum(), cart.getMoney());
            orderDetails.add(orderDetail);
            sum = sum.add(cart.getMoney());
        }
        //3.创建订单
        Order order = new Order(oid, user.getId(), sum, "1", new Date(), Integer.parseInt(aid));

        //OrderService orderService = new OrderServiceImpl();
        try {
            orderService.saveOrder(order, orderDetails);
            request.setAttribute("order", order);
            return "/orderSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "订单提交失败" + e.getMessage());
            return "/message.jsp";
        }

    }

}
