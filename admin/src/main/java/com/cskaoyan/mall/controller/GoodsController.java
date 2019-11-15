package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GoodsListResVo;
import com.cskaoyan.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    /**
     * 查询所有商品
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequestMapping("list")
    public BaseReqVo<GoodsListResVo> listGoods(int page, int limit, String sort, String order){
        BaseReqVo<GoodsListResVo> goodsBaseReqVo = new BaseReqVo<>();
        int total = goodsService.queryGoodsCounts();
        List<Goods> goods = goodsService.queryGoods(page, limit, sort, order);
        GoodsListResVo goodsListResVo = new GoodsListResVo();
        goodsListResVo.setTotal(total);
        goodsListResVo.setItems(goods);
        goodsBaseReqVo.setErrno(0);
        goodsBaseReqVo.setErrmsg("成功");
        goodsBaseReqVo.setData(goodsListResVo);
        return goodsBaseReqVo;

    }

}
