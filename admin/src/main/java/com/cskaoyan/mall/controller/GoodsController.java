package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    /**
     * 查询商品
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("list")
    public BaseReqVo<GoodsListResVo> listGoods(Integer page, Integer limit,Integer goodsSn, String name, String sort, String order){
        BaseReqVo<GoodsListResVo> goodsBaseReqVo = new BaseReqVo<>();
        int total = goodsService.queryGoodsCounts(goodsSn,name);
        List<Goods> goods = goodsService.queryGoods(page, limit,goodsSn, name, sort, order);
        GoodsListResVo goodsListResVo = new GoodsListResVo();
        goodsListResVo.setTotal(total);
        goodsListResVo.setItems(goods);
        goodsBaseReqVo.setErrno(0);
        goodsBaseReqVo.setErrmsg("成功");
        goodsBaseReqVo.setData(goodsListResVo);
        return goodsBaseReqVo;
    }

    /**
     *查询商品的种类(Lv1 ,Lv2)和品牌
     * @return
     */

    @GetMapping("catAndBrand")
    public BaseReqVo<CatAndBrandResVo> GetcatAndBrand(){
        BaseReqVo<CatAndBrandResVo> goodsBaseReqVo = new BaseReqVo<>();
        CatAndBrandResVo catAndBrandResVo = new CatAndBrandResVo();
        List<Brand> brands = goodsService.queryBrands();
        List<CatAndBrandResVo_CatElem> catAndBrandResVo_catElems = goodsService.queryNestedCategory();
        catAndBrandResVo.setBrandList(brands);
        catAndBrandResVo.setCategoryList(catAndBrandResVo_catElems);
        goodsBaseReqVo.setErrno(0);
        goodsBaseReqVo.setErrmsg("成功");
        goodsBaseReqVo.setData(catAndBrandResVo);
        return goodsBaseReqVo;
    }
}
