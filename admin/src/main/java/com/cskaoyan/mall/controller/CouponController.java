package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.bean.CouponListResVo;
import com.cskaoyan.mall.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 11:39
 **/
@RestController
@RequestMapping("admin/coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @GetMapping("list")
    public BaseReqVo<CouponListResVo> listCoupon(Integer page, Integer limit, String name, Short type,Short status, String sort, String order){
        BaseReqVo<CouponListResVo> couponListResVoBaseReqVo = new BaseReqVo<>();
        int total = couponService.queryCouponCounts();
        List<Coupon> coupons = couponService.queryCoupon(page,limit,name,type,status,sort,order);
        CouponListResVo couponListResVo = new CouponListResVo();
        couponListResVo.setItems(coupons);
        couponListResVo.setTotal(total);
        couponListResVoBaseReqVo.setErrmsg("成功");
        couponListResVoBaseReqVo.setErrno(0);
        couponListResVoBaseReqVo.setData(couponListResVo);
        return couponListResVoBaseReqVo;
    }

    @PostMapping("create")
    public BaseReqVo<Coupon> createCoupon(@RequestBody Coupon coupon){
//        System.out.println(coupon);
        int result = couponService.createCoupon(coupon);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(coupon);
        return baseReqVo;
    }


}
