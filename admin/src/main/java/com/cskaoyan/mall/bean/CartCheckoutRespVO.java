package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class CartCheckoutRespVO {
    Double grouponPrice;
    Integer grouponRulesId;
    Address checkedAddress;
    Double actualPrice;
    Double orderTotalPrice;
    Double couponPrice;
    Integer availableCouponLength;
    Integer couponId;
    Double freightPrice;
    List<Cart> checkedGoodsList;
    Double goodsTotalPrice;
    Integer addressId;
}
