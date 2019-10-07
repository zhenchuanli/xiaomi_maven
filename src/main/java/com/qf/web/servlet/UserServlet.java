package com.qf.web.servlet;

import cn.dsna.util.images.ValidateCode;
import com.qf.domain.Address;
import com.qf.domain.Order;
import com.qf.domain.User;
import com.qf.service.AddressService;
import com.qf.service.OrderService;
import com.qf.service.UserService;
import com.qf.utils.Base64Utils;
import com.qf.utils.RandomUtils;
import com.qf.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.context.ContextLoader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Oldman 2019/9/10 14:46
 * bug? 不存在的
 */
@WebServlet(name = "UserServlet", value = "/userservlet")
public class UserServlet extends BaseServlet {

    private UserService userService = (UserService) ContextLoader.getCurrentWebApplicationContext().getBean("userServiceImpl");
    private OrderService orderService = (OrderService) ContextLoader.getCurrentWebApplicationContext().getBean("orderServiceImpl");
    private AddressService addressService = (AddressService) ContextLoader.getCurrentWebApplicationContext().getBean("addressServiceImpl");
    //注册
    public String register(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("用户注册");
        //获取参数
        User user = new User();
        try {
            //判断参数合法性
            BeanUtils.populate(user, request.getParameterMap());
            String repassword = request.getParameter("repassword");
            if (StringUtils.isEmpty(user.getUsername())){
                request.setAttribute("registerMsg", "用户名不能为空");
                return "/register.jsp";
            }
            if (StringUtils.isEmpty(user.getPassword())){
                request.setAttribute("registerMsg", "密码不能为空");
                return "/register.jsp";
            }
            if (!user.getPassword().equals(repassword)) {
                request.setAttribute("registerMsg", "两次密码不相同");
                return "/register.jsp";
            }
            if (StringUtils.isEmpty(user.getEmail())){
                request.setAttribute("registerMsg", "邮箱不能为空");
                return "/register.jsp";
            }


            //调用业务
            //UserService userService = new UserServiceImpl();
            // flag role code
            user.setFlag(0);
            user.setRole(1);
            user.setCode(RandomUtils.createActive());
            System.out.println(user.toString());
            userService.register(user);
            return "redirect:/registerSuccess.jsp";



        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("registerMsg", "注册失败");
        }

        //System.out.println("用户注册");
        return "/register.jsp";
    }

    //检查用户名
    public String checkUserName(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String username = request.getParameter("username");
        //System.out.println("username = " + username);
        if (username == null || username.trim().length() == 0) {
            return null;
        }
        //UserService userService = new UserServiceImpl();
        User user = userService.checkUserName(username);
        if (user != null) {
            response.getWriter().write("1");
        } else {
            response.getWriter().write("0");
        }
        return null;
    }

    //验证码
    public String code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ValidateCode validateCode = new ValidateCode(100, 40, 4, 20);
        String code = validateCode.getCode();
        request.getSession().setAttribute("vcode", code);
        validateCode.write(response.getOutputStream());
        return null;
    }

    //判断验证码是否正确
    public String checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String client_code = request.getParameter("code");
        String server_code = (String) request.getSession().getAttribute("vcode");
        if (StringUtils.isEmpty(client_code)) {
            return null;
        }
        if (client_code.equalsIgnoreCase(server_code)) {
            response.getWriter().write("0");
        } else {
            response.getWriter().write("1");
        }
        return null;
    }


    //登录
    public String login(HttpServletRequest request, HttpServletResponse response) {
        //System.out.println("用户登录");
        //获取用户名，密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String valcode = request.getParameter("valcode");
        String vcode = (String) request.getSession().getAttribute("vcode");
        String auto = request.getParameter("auto");
        if (!valcode.equalsIgnoreCase(vcode)) {
            request.setAttribute("msg", "验证码错误");
            return "/login.jsp";
        }
        if (StringUtils.isEmpty(username)) {
            request.setAttribute("msg", "用户名不能为空");
            return "/login.jsp";
        }
        if (StringUtils.isEmpty(password)) {
            request.setAttribute("msg", "密码不能为空");
            return "/login.jsp";
        }

        if (StringUtils.isEmpty(valcode)) {
            request.setAttribute("msg", "验证码不能为空");
            return "/login.jsp";
        }


        //验证密码用户名是否正确
        //UserService userService = new UserServiceImpl();
        //（在service层）先把密码解密，因为从前端获取的是用户输入的密码，数据库存储的是加密过后的密码
        User user = userService.login(username, password);
        if (user == null) {
            request.setAttribute("msg", "用户名或密码有误");
            return "/login.jsp";
        } else {
            //有用户
            //判断是否激活
            if (user.getFlag() != 1) {
                //未激活
                request.setAttribute("msg", "用户尚未激活或禁用");
                return "/login.jsp";
            }
            // 是否有权限
            if (user.getRole() != 1) {
                request.setAttribute("msg", "用户没有权限");
                return "/login.jsp";
            }

            //登录成功，放入session
            request.getSession().setAttribute("user", user);
            //如果勾选按钮，则设置cookie（通过过滤器去自动登录）
            if (auto != null) {
                String userinfo = username + "#" + password;
                Cookie cookie = new Cookie("userinfo", Base64Utils.encode(userinfo));
                cookie.setMaxAge(60*60*24*14);
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }

            System.out.println("---------------------------");
            //重定向到首页
            return "redirect:/index.jsp";
        }
    }

    //注销
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();

        Cookie cookie = new Cookie("userinfo", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "redirect:/index.jsp";
    }

    //获取收货地址，更新后可以再执行一次
    public String getAddress(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        //AddressService addressService = new AddressServiceImpl();
        List<Address> addList = addressService.findByUid(user.getId());
        request.getSession().setAttribute("addList", addList);

        return "/self_info.jsp";
    }

    //添加收货地址
    public String addAddress(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        //获取数据
        String name = request.getParameter("name");  //收件人
        String phone = request.getParameter("phone");  //电话
        String detail = request.getParameter("detail");   //详细地址
        if (StringUtils.isEmpty(name)) {
            request.setAttribute("msg", "收件人不能为空");
            //return "/userservlet?method=getAddress";
            return getAddress(request, response);
        }
        if (StringUtils.isEmpty(phone)) {
            request.setAttribute("msg", "电话不能为空");
            return getAddress(request, response);
        }
        if (StringUtils.isEmpty(detail)) {
            request.setAttribute("msg", "地址不能为空");
            return getAddress(request, response);
        }

        Address address = new Address(null, detail, name, phone, user.getId(), 0);
        //AddressService addressService = new AddressServiceImpl();
        addressService.add(address);

        return getAddress(request, response);
    }

    //设置默认id
    public String defaultAddress(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        String id = request.getParameter("id");
        //AddressService addressService = new AddressServiceImpl();
        addressService.updateDefault(Integer.parseInt(id), user.getId());
        return getAddress(request, response);
    }

    //删除地址
    public String deleteAddress(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }

        //前端获取地址id
        String id = request.getParameter("id");

        //AddressService addressService = new AddressServiceImpl();
        addressService.deleteById(Integer.parseInt(id));

        return getAddress(request, response);
    }


    //修改地址
    public String updateAddress(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        //获取前端数据
        String id = request.getParameter("id");  //隐藏
        String level = request.getParameter("level");  //隐藏
        String detail = request.getParameter("detail");
        String phone = request.getParameter("phone");
        String name = request.getParameter("name");

        //有问题！！！！！！！！！！！！！
        if (StringUtils.isEmpty(name)) {
            response.getWriter().write("<script type='text/javascript'>alert('收件人不能为空');window.location='userservlet?method=getAddress'</script>");
            return null;
        }
        if (StringUtils.isEmpty(phone)) {
            response.getWriter().write("<script type='text/javascript'>alert('收件人不能为空');window.location='userservlet?method=getAddress'</script>");
            return null;
        }
        if (StringUtils.isEmpty(detail)) {
            response.getWriter().write("<script type='text/javascript'>alert('收件人不能为空');window.location='userservlet?method=getAddress'</script>");
            return null;
        }
        //有问题！！！！！！！！！！！！！

        Address address = new Address(Integer.parseInt(id), detail, name, phone, user.getId(), Integer.parseInt(level));

        //AddressService addressService = new AddressServiceImpl();
        addressService.update(address);

        return getAddress(request, response);
    }

    //我的订单
    public String getOrderList(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/login.jsp";
        }
        //OrderService orderService = new OrderServiceImpl();
        List<Order> orderList = orderService.findAllByUserId(user.getId());
        request.setAttribute("orderList", orderList);
        //return getAddress(request, response);
        return "/orderList.jsp";
    }
}
