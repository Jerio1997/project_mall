package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class CartReqTo {
    int goodsId;
    short number;
    int productId;
}
