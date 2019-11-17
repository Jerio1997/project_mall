package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.bean.CouponListResVo;
import com.cskaoyan.mall.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @PostMapping("delete")
    public BaseReqVo deleteCoupon(@RequestBody Coupon coupon){

        int result = couponService.deleteCoupon(coupon);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @PostMapping("update")
    public BaseReqVo updateCoupon(@RequestBody Coupon coupon){
        coupon.setUpdateTime(new Date());
        int result = couponService.updateCoupon(coupon);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(coupon);
        return baseReqVo;
    }

    @GetMapping("read")
    public BaseReqVo readCoupon(Integer id){
        Coupon coupon = couponService.getCouponById(id);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(coupon);
        return baseReqVo;
    }

    /*

    这个接口的没有返回参数的格式，需要给到前台才能够继续写
    这个接口位于优惠券->详情->listuser接口（下面那个）


    @GetMapping("listuser")
    public BaseReqVo listuserCoupon(){

    }*/





}
