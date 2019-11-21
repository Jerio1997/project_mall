package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.OrderGoods;
import com.cskaoyan.mall.mapper.OrderGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderGoodsServiceImpl implements OrderGoodsService {

    @Autowired
    OrderGoodsMapper orderGoodsMapper;

    @Override
    public int insertOrderGoods(OrderGoods orderGoods) {
        int i = orderGoodsMapper.insertSelective(orderGoods);
        return i;
    }
}
