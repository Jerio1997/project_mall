package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GoodsListResVo_Wx;
import com.cskaoyan.mall.service.CategoryService;
import com.cskaoyan.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("wx/goods")
public class WxGoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("count")
    public BaseReqVo goodsCount() {
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        int i = goodsService.queryGoodsCounts(null, null);
        baseReqVo.setData(i);
        return baseReqVo;
    }
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

}
