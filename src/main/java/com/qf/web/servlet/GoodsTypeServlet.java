package com.qf.web.servlet;

import com.alibaba.fastjson.JSON;
import com.qf.domain.GoodsType;
import com.qf.service.GoodsTypeService;
import com.qf.service.impl.GoodsTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Oldman 2019/9/11 10:25
 * bug? 不存在的
 */
@WebServlet(name = "GoodsTypeServlet", value = "/goodstypeservlet")
public class GoodsTypeServlet extends BaseServlet {
    //@Autowired
    private GoodsTypeService goodsTypeService = (GoodsTypeService) ContextLoader.getCurrentWebApplicationContext().getBean("goodsTypeServiceImpl");

    public String goodstypelist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        //获取商品类型  不能直接new，因为整合了
        //GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
        //调用方法
        List<GoodsType> goodsTypeList = goodsTypeService.findTypeByLevel(1);
        //使用fastjson把集合转成字符串
        String json = JSON.toJSONString(goodsTypeList);
        //System.out.println("json = " + json);
        //返回给浏览器
        response.getWriter().write(json);
        return null;
    }
}
