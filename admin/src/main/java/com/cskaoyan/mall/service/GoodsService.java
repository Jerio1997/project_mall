package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Goods;

import java.util.List;

public interface GoodsService {
    int queryGoodsCounts();
    List<Goods> queryGoods(int page,int limit,String sort,String order);
}
