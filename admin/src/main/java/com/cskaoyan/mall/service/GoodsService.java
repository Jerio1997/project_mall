package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.CatAndBrandResVo;
import com.cskaoyan.mall.bean.CatAndBrandResVo_CatElem;
import com.cskaoyan.mall.bean.Goods;

import java.util.List;

public interface GoodsService {
    int queryGoodsCounts(Integer goodsSn, String name);
    List<Goods> queryGoods(Integer page, Integer limit,Integer goodsSn, String name, String sort, String order);
    List<Brand> queryBrands();
    List<CatAndBrandResVo_CatElem > queryNestedCategory();
}
