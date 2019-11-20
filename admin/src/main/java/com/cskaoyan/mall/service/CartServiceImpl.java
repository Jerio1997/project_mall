package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Cart;
import com.cskaoyan.mall.bean.CartExample;
import com.cskaoyan.mall.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public int getTotalCount() {
        List<Cart> carts = cartMapper.selectByExample(new CartExample());
        int totalCount = 0;
        for (Cart cart : carts) {
            totalCount += cart.getNumber();
        }
        return totalCount;
    }

    @Override
    public List<Cart> getCartListByUserId(Integer id) {
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(id);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        return carts;
    }

    @Override
    public Cart getCartByUserIdAndProductId(Integer id, Integer productId) {
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(id).andProductIdEqualTo(productId);
        List<Cart> carts = cartMapper.selectByExample(cartExample);
        if (carts.size() != 0) {
            return carts.get(0);
        }
        return null;
    }

    @Override
    public int updateCart(Cart cart) {
        int i = cartMapper.updateByPrimaryKey(cart);
        return i;
    }
}
