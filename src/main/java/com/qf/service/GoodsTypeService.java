package com.qf.service;

import com.qf.domain.GoodsType;

import java.util.List;

/**
 * Oldman 2019/9/11 10:29
 * bug? 不存在的
 */
public interface GoodsTypeService {

    List<GoodsType> findTypeByLevel(int level);
}
