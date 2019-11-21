package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.utils.OrderStatusUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/order")
public class WxOrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping("list")
    public BaseReqVo listOrder(int showType, int page, int size) {
        String username = "lanzhao";
        Short[] codeByType = OrderStatusUtils.getCodeByType(showType);
        OrderReqVo orderReqVo = orderService.getOrderListByUsernameAndCodes(page, size, codeByType, username);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(orderReqVo);
        return baseReqVo;
    }

    /*@RequestMapping("submit")
    public BaseReqVo submitOrder(int cartId, int addressId, int couponId, String message, int grouponRulesId, int grouponLinkId) {
        Order order = new Order();

    }*/

    @RequestMapping("detail")
    public BaseReqVo orderDetail(Integer orderId) {
        BaseReqVo baseReqVo = new BaseReqVo();
        List<OrderGoods> orderGoods = orderService.selectOrderGoodsByOrderId(orderId);
        Map orderInfo = orderService.selectOrderInfoById(orderId);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("orderGoods", orderGoods);
        hashMap.put("orderInfo", orderInfo);
        baseReqVo.setData(hashMap);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @PostMapping("delete")
    public BaseReqVo deleteOrder(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        orderService.deleteOrder(orderId);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

}
