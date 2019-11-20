package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.CartService;
import com.cskaoyan.mall.service.GoodsService;
import com.cskaoyan.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @RequestMapping("add")
    public BaseReqVo addCart(@RequestBody CartReqTo cartReqTo) {
        Integer goodsId = cartReqTo.getGoodsId();
        Short number = cartReqTo.getNumber();
        Integer productId = cartReqTo.getProductId();
        String username = "lanzhao";
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
            int status = cartService.updateCart(cart);
        } else {
            int status = cartService.addCart(cart);
        }
        int totalCount = cartService.getTotalCount();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(totalCount);
        return baseReqVo;
    }

    @RequestMapping("index")
    public BaseReqVo listCart() {
        String username = "lanzhao";
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
    public BaseReqVo updateChecked(@RequestBody CartCheckedReqTo cartCheckedReqTo) {
        String username = "lanzhao";
        Boolean checked = false;
        User userByUsername = userService.getUserByUsername(username);
        if (cartCheckedReqTo.getIsChecked() == 1) {
            checked = true;
        }
        Integer[] productIds = cartCheckedReqTo.getProductIds();
        for (Integer productId : productIds) {
            Cart cartByUserIdAndProductId = cartService.getCartByUserIdAndProductId(userByUsername.getId(), productId);
            cartByUserIdAndProductId.setChecked(checked);
            int i = cartService.updateCart(cartByUserIdAndProductId);
        }
        BaseReqVo baseReqVo = listCart();
        return baseReqVo;
    }

}
