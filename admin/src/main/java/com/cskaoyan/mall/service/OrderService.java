package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Order;
import com.cskaoyan.mall.bean.OrderGoods;
import com.cskaoyan.mall.bean.OrderReqVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Map<String, Object> getOrderList(int page, int limit, Short[] orderStatusArray, Integer userId, String orderSn, String sort, String order);

    List<OrderGoods> getOrderGoodsByOrderId(int orderId);

    Order getOrderById(int id);

    int InsertOrders(List<Order> orderList);

    OrderReqVo getOrderListByUsernameAndCodes(int page, int size, Short[] codeByType, String username);

<<<<<<< HEAD

    List<Order> selectOrderByUserIdAndStatus(Integer id, String unrecv);

=======
    int InsertOrder(Order order);
>>>>>>> 48567d6bd1c954377ccb7fb6be5e6068774d8ced
    List<OrderGoods> selectOrderGoodsByOrderId(Integer orderId);

    HashMap<String, Object> selectOrderInfoById(Integer orderId);

    void deleteOrder(Integer orderId);

<<<<<<< HEAD
=======
    void cancelOrderByOrderId(Integer orderId);

    void confirmOrderByOrderId(Integer orderId);
>>>>>>> 48567d6bd1c954377ccb7fb6be5e6068774d8ced
}
