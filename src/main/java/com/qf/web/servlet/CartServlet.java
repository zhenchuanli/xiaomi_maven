package com.qf.web.servlet;

import com.qf.domain.Cart;
import com.qf.domain.Goods;
import com.qf.domain.User;
import com.qf.service.CartService;
import com.qf.service.GoodsService;
import com.qf.utils.StringUtils;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Oldman 2019/9/11 15:47
 * bug? 不存在的
 */
@WebServlet(name = "CartServlet", value = "/cartservlet")
public class CartServlet extends BaseServlet {
    //从工厂中获取service

    private CartService cartService = (CartService) ContextLoader.getCurrentWebApplicationContext().getBean("cartServiceImpl");
    private GoodsService goodsService = (GoodsService) ContextLoader.getCurrentWebApplicationContext().getBean("goodsServiceImpl");

    //添加
    public String addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断是否登录，获取对象
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        //1.获取参数
        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");
        if (StringUtils.isEmpty(goodsId)){
            return "redirect:/login.jsp";
        }
        //2.添加到购物车，（添加，更新）
        //查询有没有商品用户id，商品id
        //CartService cartService = new CartServiceImpl();
        Cart cart = cartService.findByUidAndPid(user.getId(), Integer.parseInt(goodsId));
        //GoodsService goodsService = new GoodsServiceIpml();
        Goods goods = goodsService.findById(Integer.parseInt(goodsId));

        try {
            if (cart == null) {
                //添加商品
                cart = new Cart(user.getId(), Integer.parseInt(goodsId), Integer.parseInt(number), goods.getPrice().multiply(new BigDecimal(Integer.parseInt(number))));
                cartService.add(cart);
            } else {
                //更新
                cart.setNum(Integer.parseInt(number) + cart.getNum());  //设置完num了
                cart.setMoney(goods.getPrice().multiply(new BigDecimal(cart.getNum())));
                cartService.update(cart);
            }

            return "redirect:/cartSuccess.jsp";
        } catch (NumberFormatException e) {
            return "redirect:/index.jsp";
        }

    }

    //查看购物车
    public String getCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断是否登录，获取对象
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }

        //CartService cartService = new CartServiceImpl();
        List<Cart> carts = cartService.findByUid(user.getId());  //在业务里面处理

        request.setAttribute("carts", carts);
        return "/cart.jsp";
    }


    //添加，删除
    public String addCartAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断有没有登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect/login.jsp";
        }

        //获取参数
        String goodsId = request.getParameter("goodsId");
        String number = request.getParameter("number");
        //查询
        //CartService cartService = new CartServiceImpl();
        Cart cart = cartService.findByUidAndPid(user.getId(), Integer.parseInt(goodsId));
        if (cart != null) {
            if (number.equals("0")) {
                //删除
                cartService.delete(user.getId(), Integer.parseInt(goodsId));
            } else {
                //更新
                int num = Integer.parseInt(number);  // 1  -1
                //获取单价
                BigDecimal price = cart.getMoney().divide(new BigDecimal(cart.getNum()));
                //更新数量
                cart.setNum(cart.getNum() + num);
                //更新价格
                cart.setMoney(price.multiply(new BigDecimal(cart.getNum())));
                cartService.update(cart);
            }
        }
        return null;
    }

    //清空购物车
    public String clearCartAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断有没有登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect/login.jsp";
        }
        //CartService cartService = new CartServiceImpl();
        cartService.deleteByUid(user.getId());
        return null;
    }
}
