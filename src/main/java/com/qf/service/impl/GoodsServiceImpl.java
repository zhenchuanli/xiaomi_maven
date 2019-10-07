package com.qf.service.impl;

import com.qf.dao.GoodsDao;
import com.qf.dao.GoodsTypeDao;
import com.qf.domain.Goods;
import com.qf.domain.GoodsType;
import com.qf.domain.PageBean;
import com.qf.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Oldman 2019/9/11 11:20
 * bug? 不存在的
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsDao goodsDao;
    @Autowired
    GoodsTypeDao goodsTypeDao;

    @Override
    public PageBean<Goods> findPageByWhere(int pageNum, int pageSize, String condition) {

        long totalSize = goodsDao.getCount(condition);
        pageNum = (pageNum-1) * pageSize;
        List<Goods> data = goodsDao.findPageByWhere(pageNum, pageSize, condition);

        PageBean<Goods> pageBean = new PageBean<>(pageNum, pageSize, totalSize, data);
        return pageBean;
    }

    @Override
    public Goods findById(int gid) {
        Goods goods = goodsDao.findById(gid); //此时goodsType  null

        //GoodsTypeDao goodsTypeDao = new GoodsTypeDaoImpl();
        GoodsType goodsType = goodsTypeDao.findById(goods.getTypeid());

        goods.setGoodsType(goodsType);
        return goods;
    }


}
