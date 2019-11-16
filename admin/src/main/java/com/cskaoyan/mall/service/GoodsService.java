package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;

import java.util.List;

public interface GoodsService {
    int queryGoodsCounts(Integer goodsSn, String name);
    List<Goods> queryGoods(Integer page, Integer limit,Integer goodsSn, String name, String sort, String order);
    List<CatAndBrandResVo_CatElemChild> queryBrands();
    List<CatAndBrandResVo_CatElem > queryNestedCategory();
    int CreateGoods(GoodsCreatedResVo goodsCreatedResVo);
    GoodsDetailReqVo getGoodsDetail(Integer id);

}
