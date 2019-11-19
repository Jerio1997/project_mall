package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/home")
public class WxHomeController {
    @Autowired
    GoodsService goodsService;

    @Autowired
    CouponService couponService;

    @Autowired
    GrouponRulesService grouponRulesService;

    @Autowired
    AdService adService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    BrandService brandService;

    @Autowired
    TopicService topicService;

    @RequestMapping("index")
    public BaseReqVo index() {
        BaseReqVo baseReqVo = new BaseReqVo();
        HashMap<String, Object> data = new HashMap<>();
        List<Goods> newGoodsList = goodsService.getNewGoodsList(1, 10);
        // newGoodsList
        data.put("newGoodsList", newGoodsList);
        List<Goods> hotGoodsList = goodsService.getHotGoodsList(1, 10);
        // hotGoodsList
        data.put("hotGoodsList", hotGoodsList);
        CouponListResVo couponListResVo = couponService.queryCoupon(1, 10, null, null, null, "add_time", "desc");
        // couponList
        data.put("couponList", couponListResVo.getItems());
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(data);
        AdListResVo adListResVo = adService.queryListAd(1, 10, null, null, "add_time", "desc");
        // banner
        data.put("banner", adListResVo.getItems());
        List<Category> categoryList = categoryService.getCategoryList(1, 12);
        // channel
        data.put("channel", categoryList);
        GrouponRulesListResVo grouponRulesListResVo = grouponRulesService.queryGrouponRules(1, 10, null, "add_time", "desc");
        List<GrouponRules> items = grouponRulesListResVo.getItems();
        List<Map<String, Object>> grouponList = new ArrayList<>();
        for (GrouponRules groupon : items) {
            Map<String, Object> grouponMap = new HashMap<>();
            Integer goodsId = groupon.getGoodsId();
            Goods goods = goodsService.queryGoodsById(goodsId);
            grouponMap.put("goods", goods);
            grouponMap.put("groupon_member", groupon.getDiscountMember());
            Double retailPrice = goods.getRetailPrice().doubleValue();
            Double discount = groupon.getDiscount().doubleValue();
            grouponMap.put("groupon_price", retailPrice - discount);
            grouponList.add(grouponMap);
        }
        // grouponList
        data.put("grouponList", grouponList);
        Map<String, Object> brandList = brandService.getBrandList(1, 20, null, null, "add_time", "desc");
        data.put("brandList", brandList.get("items"));
        TopicListResVo topicListResVo = topicService.queryTopic(1, 20, null, null, "add_time", "desc");
        data.put("topicList", topicListResVo.getItems());
        List<FloorGoodsResVo> floorGoodsList = new ArrayList<>();
        for (Category category : categoryList) {
            FloorGoodsResVo floorGoodsResVo = new FloorGoodsResVo();
            floorGoodsResVo.setId(category.getId());
            floorGoodsResVo.setName(category.getName());
            List<Goods> goodsList = goodsService.queryGoodsByCategoryLevel1(1, 6, category.getId());
            floorGoodsResVo.setGoodsList(goodsList);
            floorGoodsList.add(floorGoodsResVo);
        }
        data.put("floorGoodsList", floorGoodsList);
        return baseReqVo;
    }
}
