package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Goods;

import java.util.List;

public interface GoodsService {
    int queryGoodsCounts();
    List<Goods> queryGoods(Integer page, Integer limit,Integer goodsSn, String name, String sort, String order);
}
