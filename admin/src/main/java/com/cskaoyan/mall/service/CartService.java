package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Cart;

import java.util.List;

public interface CartService {
    int addCart(Cart cart);

    //获取购物车总数
    int getTotalCount();

    List<Cart> getCartListByUserId(Integer id);

    Cart getCartByUserIdAndProductId(Integer id, Integer productId);

    int updateCart(Cart cart);
}
