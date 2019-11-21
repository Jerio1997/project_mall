package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class OrderSubminReqDTO {
    int cartId;
    int addressId;
    int couponId;
    String message;
    int grouponRulesId;
    int grouponLinkId;
}
