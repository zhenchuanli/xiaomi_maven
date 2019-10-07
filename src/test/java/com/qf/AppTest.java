package com.qf;

import com.qf.dao.GoodsDao;
import com.qf.dao.GoodsTypeDao;
import com.qf.domain.Goods;
import com.qf.domain.GoodsType;
import com.qf.domain.PageBean;
import com.qf.service.GoodsService;
import com.qf.service.GoodsTypeService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Oldman 2019/10/7 10:07
 * bug? 不存在的
 */
public class AppTest {

    @Test
    public void testDao() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        GoodsTypeDao goodsTypeDao = context.getBean("goodsTypeDao", GoodsTypeDao.class);
        List<GoodsType> goodsTypes = goodsTypeDao.findByLevel(1);
        for (GoodsType goodsType : goodsTypes) {
            System.out.println(goodsType);
        }
    }

    @Test
    public void testService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        GoodsTypeService goodsTypeServiceImpl = context.getBean("goodsTypeServiceImpl", GoodsTypeService.class);
        List<GoodsType> typeByLevel = goodsTypeServiceImpl.findTypeByLevel(1);
        for (GoodsType goodsType : typeByLevel) {
            System.out.println(goodsType);
        }
    }

    @Test
    public void testGoodsDao() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        GoodsDao goodsDao = context.getBean("goodsDao", GoodsDao.class);
        List<Goods> pageByWhere = goodsDao.findPageByWhere(1, 8, "typeid=1");
        for (Goods goods : pageByWhere) {
            System.out.println(goods);
        }
    }

    @Test
    public void testGoodsService() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        GoodsService goodsService = context.getBean("goodsServiceImpl", GoodsService.class);
        PageBean<Goods> pageByWhere = goodsService.findPageByWhere(1, 8, "typeid=1");
        for (Goods goods : pageByWhere.getData()) {
            System.out.println(goods);
        }
    }

    @Test
    public void testGoodsDaoCount() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        GoodsDao goodsDao = context.getBean("goodsDao", GoodsDao.class);
        long count = goodsDao.getCount("typeid=1");
        System.out.println("count = " + count);
    }

}
