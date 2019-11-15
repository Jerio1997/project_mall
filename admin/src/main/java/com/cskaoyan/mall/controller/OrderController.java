package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Order;
import com.cskaoyan.mall.bean.OrderGoods;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("admin/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @RequestMapping("list")
    public BaseReqVo listOrder(int page, int limit, Short[] orderStatusArray, Integer userId, String orderSn, String sort, String order) {
        Map<String, Object> data = orderService.getOrderList(page, limit, orderStatusArray, userId, orderSn, sort, order);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(data);
        return baseReqVo;
    }

    @RequestMapping("detail")
    public BaseReqVo orderDetail(int id) { // 此id为 orderId
        List<OrderGoods> orderGoodsByOrderId = orderService.getOrderGoodsByOrderId(id);
        Order order = orderService.getOrderById(id);
        User user = userService.getUserById(order.getUserId());
        User user1 = new User(); //防止个人隐私暴露
        user1.setAvatar(user.getAvatar());
        user1.setNickname(user.getNickname());
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        Map<String, Object> data = new HashMap<>();
        data.put("orderGoods", orderGoodsByOrderId);
        data.put("user", user1);
        data.put("order", order);
        baseReqVo.setData(data);
        return baseReqVo;
    }
}
