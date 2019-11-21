package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/cart")
public class WxCartController {

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    GrouponRulesService grouponRulesService;

    @Autowired  // 应该自动注入一个 addressService
    AddressService addressService;

    @Autowired
    CouponService couponService;

    @Autowired
    SystemService systemService;



    @RequestMapping("add")
    public BaseReqVo addCart(@RequestBody CartReqTo cartReqTo, HttpServletRequest request) {
        Integer goodsId = cartReqTo.getGoodsId();
        Short number = cartReqTo.getNumber();
        Integer productId = cartReqTo.getProductId();
        Subject subject = SecurityUtils.getSubject();
        User userAuth = (User) subject.getPrincipal();
        String username = userAuth.getUsername();
        User userByUsername = userService.getUserByUsername(username);
        // 相同product的商品加入购物车时购物车只有一条记录，即用update而不是add
        Cart cartByUserIdAndProductId = cartService.getCartByUserIdAndProductId(userByUsername.getId(), productId);
        Cart cart = new Cart();
        cart.setUserId(userByUsername.getId());
        cart.setGoodsId(goodsId);
        Goods goodsById = goodsService.getGoodsById(goodsId);
        cart.setGoodsSn(goodsById.getGoodsSn());
        cart.setGoodsName(goodsById.getName());
        GoodsProduct goodsProductById = goodsService.getGoodsProductById(productId);
        cart.setProductId(productId);
        cart.setPrice(goodsProductById.getPrice());
        cart.setNumber(number);
        cart.setSpecifications(goodsProductById.getSpecifications());
        cart.setChecked(true);
        cart.setPicUrl(goodsProductById.getUrl());
        cart.setAddTime(new Date());
        cart.setUpdateTime(new Date());
        cart.setDeleted(false);
        if (cartByUserIdAndProductId != null) {
            cart.setId(cartByUserIdAndProductId.getId());
            cart.setNumber((short) (cart.getNumber() + cartByUserIdAndProductId.getNumber()));
            cart.setChecked(cartByUserIdAndProductId.getChecked());
            if (request.getRequestURI().contains("fastadd")) {
                cart.setNumber(number);
            }
            int status = cartService.updateCart(cart);
        } else {
            int status = cartService.addCart(cart);
        }

        int totalCount = (int) getgoodsCount().getData();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(totalCount);
        return baseReqVo;
    }


    @RequestMapping("fastadd")
    public BaseReqVo fastaddCart(@RequestBody CartReqTo cartReqTo, HttpServletRequest request) {
        Integer goodsId = cartReqTo.getGoodsId();
        Short number = cartReqTo.getNumber();
        Integer productId = cartReqTo.getProductId();
        Subject subject = SecurityUtils.getSubject();
        User userAuth = (User) subject.getPrincipal();
        String username = userAuth.getUsername();
        User userByUsername = userService.getUserByUsername(username);
        // 相同product的商品加入购物车时购物车只有一条记录，即用update而不是add
        Cart cartByUserIdAndProductId = cartService.getCartByUserIdAndProductId(userByUsername.getId(), productId);
        Cart cart = new Cart();
        cart.setUserId(userByUsername.getId());
        cart.setGoodsId(goodsId);
        Goods goodsById = goodsService.getGoodsById(goodsId);
        cart.setGoodsSn(goodsById.getGoodsSn());
        cart.setGoodsName(goodsById.getName());
        GoodsProduct goodsProductById = goodsService.getGoodsProductById(productId);
        cart.setProductId(productId);
        cart.setPrice(goodsProductById.getPrice());
        cart.setNumber(number);
        cart.setSpecifications(goodsProductById.getSpecifications());
        cart.setChecked(true);
        cart.setPicUrl(goodsProductById.getUrl());
        cart.setAddTime(new Date());
        cart.setUpdateTime(new Date());
        cart.setDeleted(false);
        if (cartByUserIdAndProductId != null) {
            cart.setId(cartByUserIdAndProductId.getId());
            cart.setChecked(cartByUserIdAndProductId.getChecked());
            cart.setNumber(number);
            int status = cartService.updateCart(cart);
        } else {
            int status = cartService.addCart(cart);
        }
        Cart cart1 = cartService.getCartByUserIdAndProductId(userByUsername.getId(), productId);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(cart1.getId());
        return baseReqVo;
    }

    @RequestMapping("index")
    public BaseReqVo listCart() {
        Subject subject = SecurityUtils.getSubject();
        User userAuth = (User) subject.getPrincipal();
        String username = userAuth.getUsername();
        User userByUsername = userService.getUserByUsername(username);
        List<Cart> cartListByUserId = cartService.getCartListByUserId(userByUsername.getId());
        CartTotal cartTotal = new CartTotal();
        int goodsCount = 0;
        int checkedGoodsCount = 0;
        double goodsAmount = 0.0;
        double checkedGoodsAmount = 0.0;
        for (Cart cart : cartListByUserId) {
            if (cart.getChecked() == true) {
                checkedGoodsCount += cart.getNumber();
                checkedGoodsAmount += cart.getPrice().doubleValue() * cart.getNumber();
            }
            goodsCount += cart.getNumber();
            goodsAmount += cart.getPrice().doubleValue() * cart.getNumber();
        }
        cartTotal.setGoodsCount(goodsCount);
        cartTotal.setGoodsAmount(goodsAmount);
        cartTotal.setCheckedGoodsCount(checkedGoodsCount);
        cartTotal.setCheckedGoodsAmount(checkedGoodsAmount);
        CartResVo cartResVo = new CartResVo();
        cartResVo.setCartList(cartListByUserId);
        cartResVo.setCartTotal(cartTotal);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(cartResVo);
        return baseReqVo;
    }

    @RequestMapping("checked")
    public BaseReqVo updateChecked(@RequestBody CartCheckedReqDTO cartCheckedReqDTO) {
        Subject subject = SecurityUtils.getSubject();
        User userAuth = (User) subject.getPrincipal();
        String username = userAuth.getUsername();
        Boolean checked = false;
        User userByUsername = userService.getUserByUsername(username);
        if (cartCheckedReqDTO.getIsChecked() == 1) {
            checked = true;
        }
        Integer[] productIds = cartCheckedReqDTO.getProductIds();
        for (Integer productId : productIds) {
            Cart cartByUserIdAndProductId = cartService.getCartByUserIdAndProductId(userByUsername.getId(), productId);
            cartByUserIdAndProductId.setChecked(checked);
            int i = cartService.updateCart(cartByUserIdAndProductId);
        }
        BaseReqVo baseReqVo = listCart();
        return baseReqVo;
    }

    @RequestMapping("update")
    public BaseReqVo updateCart(@RequestBody CartUpdateReqDTO cartUpdateReqDTO) {
        Subject subject = SecurityUtils.getSubject();
        User userAuth = (User) subject.getPrincipal();
        String username = userAuth.getUsername();
        User user = userService.getUserByUsername(username);
        Integer productId = cartUpdateReqDTO.getProductId();
        GoodsProduct goodsProductById = goodsService.getGoodsProductById(productId);
        BaseReqVo baseReqVo = new BaseReqVo();
        if (goodsProductById.getNumber() < cartUpdateReqDTO.getNumber()) {
            baseReqVo.setErrmsg("库存不足");
            baseReqVo.setErrno(710);
            return baseReqVo;
        }
        Cart cart = new Cart();
        cart.setId(cartUpdateReqDTO.getId());
        cart.setNumber(cartUpdateReqDTO.getNumber().shortValue());
        cartService.updateCart(cart);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("delete")
    public BaseReqVo deleteCart(@RequestBody CartDeleteReqDTO cartDeleteReqDTO) {
        Subject subject = SecurityUtils.getSubject();
        User userAuth = (User) subject.getPrincipal();
        String username = userAuth.getUsername();
        User user = userService.getUserByUsername(username);
        Integer[] productIds = cartDeleteReqDTO.getProductIds();
        for (Integer productId : productIds) {
            Cart cart = cartService.getCartByUserIdAndProductId(user.getId(), productId);
            cart.setDeleted(true);
            int i = cartService.updateCart(cart);
        }
        BaseReqVo baseReqVo = listCart();
        return baseReqVo;
    }

    @RequestMapping("goodscount")
    public BaseReqVo getgoodsCount() {
        Subject subject = SecurityUtils.getSubject();
        User userAuth = (User) subject.getPrincipal();
        String username = userAuth.getUsername();
        User user = userService.getUserByUsername(username);
        List<Cart> cartListByUserId = cartService.getCartListByUserId(user.getId());
        Integer goodsCount = 0;
        for (Cart cart : cartListByUserId) {
            goodsCount += cart.getNumber();
        }
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(goodsCount);
        return baseReqVo;
    }

    @RequestMapping("checkout")
    public BaseReqVo checkout(Integer cartId, Integer addressId, Integer couponId, Integer grouponRulesId) {
        Subject subject = SecurityUtils.getSubject();
        User userAuth = (User) subject.getPrincipal();
        String username = userAuth.getUsername();
        User user = userService.getUserByUsername(username);
        double grouponPrice = 0;
        if (grouponRulesId != 0) {
            GrouponRules grouponRulesById = grouponRulesService.getGrouponRulesById(grouponRulesId);
            grouponPrice = grouponRulesById.getDiscount().doubleValue();
        }
        Address addressById = addressService.getAddressById(addressId);
        double couponPrice = 0;
        if (couponId != 0 && couponId != -1) {
            Coupon couponById = couponService.getCouponById(couponId);
            couponPrice = couponById.getDiscount().doubleValue();
        }
        List<Cart> cartListByUserIdAndCartId = cartService.getCartListByUserIdAndCartId(user.getId(), cartId);
        Double goodsTotalPrice = 0.0;
        for (Cart cart : cartListByUserIdAndCartId) {
            goodsTotalPrice += cart.getPrice().doubleValue() * cart.getNumber().doubleValue();
        }
        Double actualPrice = goodsTotalPrice - grouponPrice - couponPrice;
        Double expressFreightMin = systemService.getExpressFreightMin();
        Double expressFreightValue = systemService.getExpressFreightValue();
        Double orderTotalPrice = actualPrice;
        Double freightPrice = 0.0;
        if (actualPrice < expressFreightMin) {
            orderTotalPrice += expressFreightValue;
            freightPrice = expressFreightValue;
        }
        CartCheckoutRespVO cartCheckoutRespVO = new CartCheckoutRespVO();
        cartCheckoutRespVO.setGrouponPrice(grouponPrice);
        cartCheckoutRespVO.setGrouponRulesId(grouponRulesId);
        cartCheckoutRespVO.setCheckedAddress(addressById);
//        cartCheckoutRespVO.setActualPrice(actualPrice);
        cartCheckoutRespVO.setActualPrice(orderTotalPrice);
        cartCheckoutRespVO.setOrderTotalPrice(orderTotalPrice);
        cartCheckoutRespVO.setCouponPrice(couponPrice);
//        cartCheckoutRespVO.setAvailableCouponLength();
        cartCheckoutRespVO.setCouponId(couponId);
        cartCheckoutRespVO.setFreightPrice(freightPrice);
        cartCheckoutRespVO.setCheckedGoodsList(cartListByUserIdAndCartId);
        cartCheckoutRespVO.setGoodsTotalPrice(goodsTotalPrice);
        cartCheckoutRespVO.setAddressId(addressId);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(cartCheckoutRespVO);
        return baseReqVo;
    }

}
