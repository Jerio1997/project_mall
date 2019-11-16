package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Order;
import com.cskaoyan.mall.bean.OrderExample;
import com.cskaoyan.mall.bean.OrderGoods;
import com.cskaoyan.mall.bean.OrderGoodsExample;
import com.cskaoyan.mall.mapper.OrderGoodsMapper;
import com.cskaoyan.mall.mapper.OrderMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderGoodsMapper orderGoodsMapper;


    @Override
    public Map<String, Object> getOrderList(int page, int limit, Short[] orderStatusArray, Integer userId, String orderSn, String sort, String order) {
        PageHelper.startPage(page,limit);
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        if (userId != null) {
            criteria.andUserIdEqualTo(userId);
        }
        if (orderSn != null) {
            criteria.andOrderSnEqualTo(orderSn);
        }
        if (orderStatusArray != null) {
            criteria.andOrderStatusIn(Arrays.asList(orderStatusArray));
        }
        example.setOrderByClause(sort + " " + order);
        List<Order> orderList = orderMapper.selectByExample(example);
        PageInfo<Order> orderPageInfo = new PageInfo<>(orderList);
        long total = orderPageInfo.getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", orderList);
        return data;
    }

    @Override
    public List<OrderGoods> getOrderGoodsByOrderId(int orderId) {
        OrderGoodsExample example = new OrderGoodsExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(example);
        return orderGoods;
    }

    @Override
    public Order getOrderById(int id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        return order;
    }

}
