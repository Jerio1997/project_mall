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

    List<Goods> getNewGoodsList(int page, int limit);

    List<Goods> getHotGoodsList(int page, int limit);

    Goods queryGoodsById(Integer goodsId);

    List<Goods> queryGoodsByCategoryLevel1(int page, int limit, Integer id);

    List<Goods> queryGoodsByBrandId(Integer brandId, Integer page, Integer size);

    List<Goods> queryRelateGoods(Integer id );

    GoodsDetailResVo_Wx queryGoodsDetail(Integer id);

    Goods getGoodsById(int goodsId);

    GoodsProduct getGoodsProductById(int productId);
}
