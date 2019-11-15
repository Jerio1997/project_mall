package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GoodsExample;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public int queryGoodsCounts() {
        GoodsExample goodsExample = new GoodsExample();
        long total = goodsMapper.countByExample(goodsExample);
        return (int) total;
    }

    @Override
    public List<Goods> queryGoods(Integer page, Integer limit,Integer goodsSn, String name, String sort, String order) {
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setOrderByClause(orderByClause);
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if(!StringUtils.isEmpty(goodsSn)){
             criteria.andGoodsSnEqualTo(goodsSn.toString());
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);

        return goods;
    }
}
