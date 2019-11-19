package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Order;
import com.cskaoyan.mall.bean.OrderReqVo;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.utils.OrderStatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @RequestMapping("submit")
//    public BaseReqVo submitOrder(int cartId, int addressId, int couponId, String message, int grouponRulesId, int grouponLinkId) {
//        Order order = new Order();
//
//    }

}
