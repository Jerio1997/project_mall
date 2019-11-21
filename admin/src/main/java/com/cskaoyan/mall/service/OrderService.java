package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Order;
import com.cskaoyan.mall.bean.OrderGoods;
import com.cskaoyan.mall.bean.OrderReqVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<String, Object> getOrderList(int page, int limit, Short[] orderStatusArray, Integer userId, String orderSn, String sort, String order);

    List<OrderGoods> getOrderGoodsByOrderId(int orderId);

    Order getOrderById(int id);

    int InsertOrders(List<Order> orderList);

    OrderReqVo getOrderListByUsernameAndCodes(int page, int size, Short[] codeByType, String username);

    int InsertOrder(Order order);

    List<OrderGoods> selectOrderGoodsByOrderId(Integer orderId);

    HashMap<String, Object> selectOrderInfoById(Integer orderId);

    void deleteOrder(Integer orderId);

    void cancelOrderByOrderId(Integer orderId);

    void confirmOrderByOrderId(Integer orderId);

    void commitOrder(Integer orderId, Integer goodsId);
}
