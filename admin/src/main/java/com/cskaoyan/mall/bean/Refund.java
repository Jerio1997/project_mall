package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class Refund {
    Integer orderId;
    Double refundMoney;
}
