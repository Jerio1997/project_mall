package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.mapper.GoodsProductMapper;
import com.cskaoyan.mall.mapper.OrderMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;
    @Autowired
    OrderMapper orderMapper;

    @Override
    public Long selectUserCount() {
        UserExample userExample = new UserExample();
        Long count = userMapper.countByExample(userExample);
        return count;
    }

    @Override
    public Long selectGoodsCount() {
        GoodsExample goodsExample = new GoodsExample();
        Long count = goodsMapper.countByExample(goodsExample);
        return count;
    }

    @Override
    public Long selectProductsCount() {
        GoodsProductExample goodsProductExample = new GoodsProductExample();
        Long count = goodsProductMapper.countByExample(goodsProductExample);
        return count;
    }

    @Override
    public Long selectOrdersCount() {
        OrderExample orderExample = new OrderExample();
        Long count = orderMapper.countByExample(orderExample);
        return count;
    }
}
