package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Order;
import com.cskaoyan.mall.bean.OrderGoods;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<String, Object> getOrderList(int page, int limit, Short[] orderStatusArray, Integer userId, String orderSn, String sort, String order);

    List<OrderGoods> getOrderGoodsByOrderId(int orderId);

    Order getOrderById(int id);
}
