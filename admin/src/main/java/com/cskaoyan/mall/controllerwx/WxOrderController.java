package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.*;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.utils.OrderStatusUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("wx/order")
public class WxOrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    AddressService addressService;

    @Autowired
    CartService cartService;

    @Autowired
    SystemService systemService;

    @Autowired
    CouponService couponService;

    @Autowired
    GrouponService grouponService;

    @Autowired
    GrouponRulesService grouponRulesService;


    @RequestMapping("list")
    public BaseReqVo listOrder(int showType, int page, int size) {
        Subject subject = SecurityUtils.getSubject();
        User userAuth = (User) subject.getPrincipal();
        String username = userAuth.getUsername();
        Short[] codeByType = OrderStatusUtils.getCodeByType(showType);
        OrderReqVo orderReqVo = orderService.getOrderListByUsernameAndCodes(page, size, codeByType, username);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(orderReqVo);
        return baseReqVo;
    }


    @RequestMapping("submit")
    public BaseReqVo submitOrder(@RequestBody OrderSubminReqDTO orderSubminReqDTO) {
        int addressId = orderSubminReqDTO.getAddressId();
        int cartId = orderSubminReqDTO.getCartId();
        int couponId = orderSubminReqDTO.getCouponId();
        int grouponLinkId = orderSubminReqDTO.getGrouponLinkId();
        int grouponRulesId = orderSubminReqDTO.getGrouponRulesId();
        String message = orderSubminReqDTO.getMessage();
        Subject subject = SecurityUtils.getSubject();
        User userAuth = (User) subject.getPrincipal();
        String username = userAuth.getUsername();
        User user = userService.getUserByUsername(username);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String orderSn = simpleDateFormat.format(date);
        orderSn += user.getId();
        Random random = new Random();
        int i = random.nextInt(10000);
        orderSn += i;
        Short orderStatus = 101;
        Address addressById = addressService.getAddressById(addressId);
        String consignee = addressById.getName();
        String mobile = addressById.getMobile();
        String address = addressById.getAddress();
        Double goodsPrice = 0.0;
        if (cartId == 0) {
            List<Cart> cartListByUserId = cartService.getCartListByUserId(user.getId());
            for (Cart cart : cartListByUserId) {
                goodsPrice += cart.getPrice().doubleValue() * cart.getNumber();
            }
        } else {
            Cart cartById = cartService.getCartById(cartId);
            goodsPrice += cartById.getPrice().doubleValue() * cartById.getNumber();
        }
        Double expressFreightMin = systemService.getExpressFreightMin();
        Double expressFreightValue = systemService.getExpressFreightValue();
        Double freightPrice = 0.0;
        if (goodsPrice <= expressFreightMin) {
            freightPrice = systemService.getExpressFreightValue();
        } else {
            freightPrice = 0.0;
        }
        Double couponPrice = 0.0;
        if (couponId != 0 && couponId != -1) {
            Coupon couponById = couponService.getCouponById(couponId);
            couponPrice += couponById.getDiscount().doubleValue();
        }
        Double integralPrice = 0.0;
        Double grouponPrice = 0.0;
        if (grouponRulesId != 0) {
            GrouponRules grouponRulesById = grouponRulesService.getGrouponRulesById(grouponRulesId);
            grouponPrice = grouponRulesById.getDiscount().doubleValue();
        }
        Double orderPrice = goodsPrice + freightPrice - couponPrice;
        Double actualPrice = orderPrice - integralPrice;
        Order order = new Order();
        order.setUserId(user.getId());
        order.setOrderSn(orderSn);
        order.setOrderStatus(orderStatus);
        order.setConsignee(consignee);
        order.setMobile(mobile);
        order.setAddress(address);
        order.setMessage(message);
        order.setGoodsPrice(new BigDecimal(goodsPrice));
        order.setFreightPrice(new BigDecimal(freightPrice));
        order.setCouponPrice(new BigDecimal(couponPrice));
        order.setIntegralPrice(new BigDecimal(integralPrice));
        order.setGrouponPrice(new BigDecimal(grouponPrice));
        order.setOrderPrice(new BigDecimal(orderPrice));
        order.setActualPrice(new BigDecimal(actualPrice));
        order.setAddTime(new Date());
        order.setUpdateTime(new Date());
        order.setDeleted(false);
        int status = orderService.InsertOrder(order);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        Map<String, Integer> data = new HashMap<>();
        data.put("orderId", order.getId());
        baseReqVo.setData(data);
        return baseReqVo;
    }

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

    @PostMapping({"cancel","refund"})
    public BaseReqVo cancelOrder(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        orderService.cancelOrderByOrderId(orderId);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @PostMapping("prepay")
    public BaseReqVo orderPrepay() {
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("订单不能支付");
        baseReqVo.setErrno(724);
        return baseReqVo;
    }

    @PostMapping("confirm")
    public BaseReqVo confirmOrder(@RequestBody Map map) {
        Integer orderId = (Integer) map.get("orderId");
        orderService.confirmOrderByOrderId(orderId);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

}
