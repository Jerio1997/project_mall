package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.*;
import com.cskaoyan.mall.service.OrderService;
import com.cskaoyan.mall.utils.OrderStatusUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    OrderGoodsService orderGoodsService;



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
        List<Cart> cartList = new ArrayList<>();
        if (cartId == 0) {
            cartList = cartService.getCartListByUserIdChecked(user.getId());
            for (Cart cart : cartList) {
                goodsPrice += cart.getPrice().doubleValue() * cart.getNumber();
                cartService.deleteCartById(cart.getId());
                OrderGoods orderGoods = new OrderGoods();
            }
        } else {
            Cart cartById = cartService.getCartById(cartId);
            goodsPrice += cartById.getPrice().doubleValue() * cartById.getNumber();
            cartService.deleteCartById(cartById.getId());
            cartList.add(cartById);
        }
        Double expressFreightMin = systemService.getExpressFreightMin();
        Double expressFreightValue = systemService.getExpressFreightValue();
        Double freightPrice = 0.0;
        if (goodsPrice <= expressFreightMin) {
            freightPrice = expressFreightValue;
        } else {
            freightPrice = 0.0;
        }
        Double couponPrice = 0.0;
        if (couponId != 0 && couponId != -1) {
            Coupon couponById = couponService.getCouponById(couponId);
            couponPrice += couponById.getDiscount().doubleValue();
            int status = couponService.updateCouponUserStatusById(couponId, 1);

        }
        Double integralPrice = 0.0;
        Double grouponPrice = 0.0;
        if (grouponRulesId != 0 && cartId != 0) {
            GrouponRules grouponRulesById = grouponRulesService.getGrouponRulesById(grouponRulesId);
            if (grouponRulesById != null) {
                grouponPrice = grouponRulesById.getDiscount().doubleValue();
            }
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
        // 添加groupon信息到groupon表
        if (grouponRulesId != 0 && cartId != 0) {
            Groupon groupon = new Groupon();
            groupon.setOrderId(order.getId());
            groupon.setRulesId(grouponRulesId);
            groupon.setUserId(user.getId());
            groupon.setAddTime(new Date());
            groupon.setUpdateTime(new Date());
            groupon.setPayed(false);
            groupon.setDeleted(false);
            int i1 = grouponService.insertGroupon(groupon,userAuth.getId());
            //i1 为添加结果   1:成功     0：没添加成功       -3:表示已经参加过
        }
        // 添加goods到 ordergoods表中
        for (Cart cart : cartList) {
            OrderGoods orderGoods = new OrderGoods();
            orderGoods.setOrderId(order.getId());
            orderGoods.setGoodsId(cart.getGoodsId());
            orderGoods.setGoodsName(cart.getGoodsName());
            orderGoods.setGoodsSn(cart.getGoodsSn());
            orderGoods.setProductId(cart.getProductId());
            orderGoods.setNumber(cart.getNumber());
            orderGoods.setPrice(cart.getPrice());
            orderGoods.setSpecifications(cart.getSpecifications());
            orderGoods.setPicUrl(cart.getPicUrl());
            orderGoods.setComment(null);
            orderGoods.setAddTime(new Date());
            orderGoods.setUpdateTime(new Date());
            orderGoods.setDeleted(false);
            orderGoodsService.insertOrderGoods(orderGoods);
        }
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

    /**
     * 给一个订单id
     * @return
     */
    @PostMapping("prepay")
    public BaseReqVo orderPrepay(@RequestBody OrderId orderId) {
        BaseReqVo baseReqVo = new BaseReqVo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        int result  = grouponService.queryGrouponNumber(orderId.getOrderId());
        if(result == -2){
            baseReqVo.setErrmsg("团购人数不足");
            baseReqVo.setErrno(724);
        } else if(result == 0){
            baseReqVo.setErrno(0);
            baseReqVo.setErrmsg("成功下单");
        } else {
            baseReqVo.setErrmsg("网络繁忙");
            baseReqVo.setErrno(724);
        }
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

    @GetMapping("goods")
    public BaseReqVo orderGoods(Integer orderId, Integer goodsId) {
        BaseReqVo baseReqVo = new BaseReqVo();
        orderService.commitOrder(orderId, goodsId);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @PostMapping("comment")
    public BaseReqVo commentOrder(@RequestBody Map map) {
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

}
