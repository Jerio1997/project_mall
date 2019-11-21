package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.CategoryService;
import com.cskaoyan.mall.service.FootPrintService;
import com.cskaoyan.mall.service.GoodsService;
import com.cskaoyan.mall.service.SearchHistoryService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.System;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("wx/goods")
public class WxGoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    SearchHistoryService searchHistoryService;
    @Autowired
    FootPrintService footPrintService;

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
        GoodsCountResVo_Wx goodsCountResVo_wx = new GoodsCountResVo_Wx();
        goodsCountResVo_wx.setGoodsCount(i);
        baseReqVo.setData(goodsCountResVo_wx);
        return baseReqVo;
    }

    /**categoryId 不影响 filterCategoryList
     * 获得商品列表
     * @param brandId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("list")
    public BaseReqVo<GoodsListResVo_Wx> getGoodsListByCondition(String keyword,Integer brandId,Integer categoryId,Boolean isHot,Boolean isNew, Integer page,String sort,String order, Integer size){
        BaseReqVo<GoodsListResVo_Wx> baseReqVo = new BaseReqVo<>();
        GoodsListResVo_Wx goodsListResVo_wx = new GoodsListResVo_Wx();
        List<Category> filterCategoryList = goodsService.queryCategoryByGoodsCodition(keyword, brandId, isHot, isNew, page, sort, order, size);
        List<Goods> goodsList = goodsService.queryGoodsByCondition(keyword,brandId,categoryId,isHot,isNew,page,sort,order,size);
        if(!StringUtils.isEmpty(keyword)){
            searchHistoryService.addHistoryKeyword(keyword);
        }
        int count = goodsList.size();
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
    @Transactional
    public BaseReqVo<GoodsDetailResVo_Wx> getGoodsDetail(Integer id){
        BaseReqVo baseReqVo = new BaseReqVo();
        GoodsDetailResVo_Wx goodsDetailResVo_wx = goodsService.queryGoodsDetail(id);
        Subject subject = SecurityUtils.getSubject();
        User principal = ((User) subject.getPrincipal());
        Integer userId = principal.getId();
        int i =  footPrintService.addFootPrint(userId,id);
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

    /**
     * 获得分类数据
     * @param id
     * @return
     */
    @GetMapping("category")
    public BaseReqVo getCategory(Integer id){
        BaseReqVo baseReqVo = new BaseReqVo();
        CategoryResVo_Wx categoryResVo_wx = categoryService.queryNestedCategory(id);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(categoryResVo_wx);
        return baseReqVo;
    }
}
