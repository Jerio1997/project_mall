package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Cart;
import com.cskaoyan.mall.bean.CartExample;
import com.cskaoyan.mall.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    CartMapper cartMapper;

    @Override
    public int addCart(Cart cart) {
        int i = cartMapper.insertSelective(cart);
        return i;
    }


    @Override
    public List<Cart> getCartListByUserId(Integer id) {
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(id).andDeletedEqualTo(false);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        return carts;
    }

    @Override
    public Cart getCartByUserIdAndProductId(Integer id, Integer productId) {
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(id).andProductIdEqualTo(productId).andDeletedEqualTo(false);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        if (carts.size() != 0) {
            return carts.get(0);
        }
        return null;
    }

    @Override
    public int updateCart(Cart cart) {
        int i = cartMapper.updateByPrimaryKeySelective(cart);
        return i;
    }

    @Override
    public List<Cart> getCartListByUserIdAndCartId(Integer userId, Integer cartId) {
        if (cartId == 0) {
            CartExample cartExample = new CartExample();
            cartExample.createCriteria().andDeletedEqualTo(false).andUserIdEqualTo(userId).andCheckedEqualTo(true);
            List<Cart> carts = cartMapper.selectByExample(cartExample);
            return carts;
        } else {
            Cart cart = cartMapper.selectByPrimaryKey(cartId);
            List<Cart> carts = new ArrayList<>();
            carts.add(cart);
            return carts;
        }
    }

    @Override
    public Cart getCartById(int cartId) {
        Cart cart = cartMapper.selectByPrimaryKey(cartId);
        return cart;
    }
}
