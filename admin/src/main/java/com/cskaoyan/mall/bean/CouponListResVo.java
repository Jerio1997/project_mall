package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 11:43
 **/
@Data
public class CouponListResVo {

    private Integer total;

    private List<Coupon> items;
}
