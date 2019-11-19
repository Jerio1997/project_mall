package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class OrderReqVo {
    int count;
    int totalPages;
    List<Order> data;
}
