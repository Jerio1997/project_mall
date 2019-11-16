package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class OrderConfig {
    private String litemall_order_comment;//未评价商品，则取消评价资格
    private String litemall_order_unpaid;//用户未付款，则订单自动取消
    private String litemall_order_unconfirm;//未确认收货，则订单自动确认收货
}
