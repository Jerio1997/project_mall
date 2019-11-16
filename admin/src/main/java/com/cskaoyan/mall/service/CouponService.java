package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Coupon;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 11:40
 **/
public interface CouponService {
    int queryCouponCounts();

    List<Coupon> queryCoupon(Integer page, Integer limit, String name, Short type, Short status, String sort, String order);

    int createCoupon(Coupon coupon);

    int deleteCoupon(Coupon coupon);

    int updateCoupon(Coupon coupon);
}
