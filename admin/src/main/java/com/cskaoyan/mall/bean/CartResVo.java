package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class CartResVo {
    CartTotal cartTotal;
    List<Cart> cartList;
}
