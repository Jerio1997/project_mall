package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.bean.CouponListResVo;
import com.cskaoyan.mall.bean.CouponUserListResVo;
import com.cskaoyan.mall.bean.Groupon;

import java.util.List;
import java.util.Map;

/**
 * Author Jerio
 * CreateTime 2019/11/16 11:40
 **/
public interface CouponService {
    int queryCouponCounts();

    CouponListResVo queryCoupon(Integer page, Integer limit, String name, Short type, Short status, String sort, String order);

    int createCoupon(Coupon coupon);

    int deleteCoupon(Coupon coupon);

    int updateCoupon(Coupon coupon);

    Coupon getCouponById(Integer id);

    CouponUserListResVo listUserCoupon(Integer page, Integer limit, Integer couponId, Integer userId, Short status, String sort, String order);

    Map<String, Object> queryCouponOnWx(Integer page, Integer size);

    int receiveCoupon(Integer couponId);

    Map<String, Object> myListCoupon(Integer page, Integer size, Short status);

    int exchangeCouponByCode(String code);
}
