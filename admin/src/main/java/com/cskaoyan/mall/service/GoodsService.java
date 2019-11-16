package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;

import java.util.List;

public interface GoodsService {
    int queryGoodsCounts(Integer goodsSn, String name);
    List<Goods> queryGoods(Integer page, Integer limit,Integer goodsSn, String name, String sort, String order);
    List<CatAndBrandResVo_CatElemChild> queryBrands();
    List<CatAndBrandResVo_CatElem > queryNestedCategory();
    int createGoods(GoodsCreatedResVo goodsCreatedResVo);
    GoodsDetailReqVo getGoodsDetail(Integer id);
    int updateGoods(GoodsCreatedResVo goodsCreatedResVo);
    int deleteGoods(Goods goods);

}
