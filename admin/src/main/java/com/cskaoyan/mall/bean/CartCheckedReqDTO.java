package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class CartCheckedReqDTO {
    Integer isChecked;
    Integer[] productIds;
}
