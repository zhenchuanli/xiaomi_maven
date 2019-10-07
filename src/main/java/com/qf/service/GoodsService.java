package com.qf.service;

import com.qf.domain.Goods;
import com.qf.domain.PageBean;

/**
 * Oldman 2019/9/11 11:20
 * bug? 不存在的
 */
public interface GoodsService {

    PageBean<Goods> findPageByWhere(int pageNum, int pageSize, String condition);

    Goods findById(int gid);

}
