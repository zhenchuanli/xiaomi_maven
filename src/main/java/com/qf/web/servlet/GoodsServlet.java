package com.qf.web.servlet;

import com.qf.domain.Goods;
import com.qf.domain.PageBean;
import com.qf.service.GoodsService;
import com.qf.utils.StringUtils;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Oldman 2019/9/11 11:17
 * bug? 不存在的
 */
@WebServlet(name = "GoodsServlet", value = "/goodsservlet")
public class GoodsServlet extends BaseServlet {

    private GoodsService goodsService = (GoodsService) ContextLoader.getCurrentWebApplicationContext().getBean("goodsServiceImpl");

    public String getGoodsListByTypeId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeId = request.getParameter("typeId");
        String _pageNum = request.getParameter("pageNum");
        String _pageSize = request.getParameter("pageSize");

        int pn = 1;
        int ps = 8;

        if (!StringUtils.isEmpty(_pageNum)) {
            pn = Integer.parseInt(_pageNum);
            if (pn < 1) {
                pn = 1;
            }
        }
        if (!StringUtils.isEmpty(_pageSize)) {
            ps = Integer.parseInt(_pageSize);
            if (ps < 1) {
                ps = 8;
            }
        }

        //System.out.println(pn + "......." + ps);

        //GoodsService goodsService = new GoodsServiceImpl();
        String condition = "";
        if (typeId != null && typeId.trim().length() != 0) {
            condition = "typeId=" + typeId;
        }
        try {
            PageBean<Goods> pageBean = goodsService.findPageByWhere(pn, ps, condition);  //typeId=1
            request.setAttribute("pageBean", pageBean);
            request.setAttribute("typeId", typeId);

            return "/goodsList.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index.jsp";
        }

    }

    public String getGoodsById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            return "redirect:/index.jsp";
        }
        //GoodsService goodsService = new GoodsServiceImpl();
        Goods goods = goodsService.findById(Integer.parseInt(id));
        request.setAttribute("goods", goods);

        return "/goodsDetail.jsp";

    }
}
