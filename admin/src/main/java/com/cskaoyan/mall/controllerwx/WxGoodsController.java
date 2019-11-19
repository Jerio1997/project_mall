package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.CategoryService;
import com.cskaoyan.mall.service.GoodsService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("wx/goods")
public class WxGoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    CategoryService categoryService;

    /**
     * 统计商品总数
     * @return
     */
    @RequestMapping("count")
    public BaseReqVo goodsCount() {
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        int i = goodsService.queryGoodsCounts(null, null);
        baseReqVo.setData(i);
        return baseReqVo;
    }

    /**
     * 获得商品列表
     * @param brandId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("list")
    public BaseReqVo<GoodsListResVo_Wx> getGoodsList(Integer brandId, Integer page, Integer size){
        BaseReqVo<GoodsListResVo_Wx> baseReqVo = new BaseReqVo<>();
        GoodsListResVo_Wx goodsListResVo_wx = new GoodsListResVo_Wx();
        List<Goods> goodsList = goodsService.queryGoodsByBrandId(brandId,page,size);
        ArrayList<Category> filterCategoryList = new ArrayList<>();
        int count = goodsList.size();
        if (count!=0){
            for (Goods goods : goodsList) {
                Integer categoryId = goods.getCategoryId();
                Category category = categoryService.getCategoryById(categoryId);
                filterCategoryList.add(category);
            }
        }
        goodsListResVo_wx.setFilterCategoryList(filterCategoryList);
        goodsListResVo_wx.setCount(count);
        goodsListResVo_wx.setGoodsList(goodsList);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(goodsListResVo_wx);
        return baseReqVo;
    }

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    @GetMapping("detail")
    public BaseReqVo<GoodsDetailResVo_Wx> getGoodsDetail(Integer id){
        BaseReqVo baseReqVo = new BaseReqVo();
        GoodsDetailResVo_Wx goodsDetailResVo_wx = goodsService.queryGoodsDetail(id);
        baseReqVo.setData(goodsDetailResVo_wx);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /**
     * 商品详情页的关联商品（大家都在看）
     * @param id
     * @return
     */
    @GetMapping("related")
    public BaseReqVo getRelatesGoods(Integer id){
        BaseReqVo baseReqVo = new BaseReqVo();
        List<Goods> goodsList = goodsService.queryRelateGoods(id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("goodsList",goodsList);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        return baseReqVo;
    }


}
