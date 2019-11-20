package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class CartUpdateReqDTO {
    Integer productId;
    Integer goodsId;
    Integer number;
    Integer id;
}
