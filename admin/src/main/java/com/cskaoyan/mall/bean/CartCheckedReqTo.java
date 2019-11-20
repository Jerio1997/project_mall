package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class CartCheckedReqTo {
    Integer isChecked;
    Integer[] productIds;
}
