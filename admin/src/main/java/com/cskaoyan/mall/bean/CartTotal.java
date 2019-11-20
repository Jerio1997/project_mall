package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class CartTotal {
    int goodsCount;
    int checkedGoodsCount;
    double goodsAmount;
    double checkedGoodsAmount;
}
