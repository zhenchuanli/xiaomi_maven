package com.qf.service.impl;

import com.qf.dao.GoodsTypeDao;
import com.qf.domain.GoodsType;
import com.qf.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Oldman 2019/9/11 10:30
 * bug? 不存在的
 */
@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {
    @Autowired
    private GoodsTypeDao goodsTypeDao;

    @Override
    public List<GoodsType> findTypeByLevel(int level) {
        return goodsTypeDao.findByLevel(level);
    }
}
