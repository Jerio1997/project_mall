package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.CouponService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author Jerio
 * CreateTime 2019/11/16 11:39
 **/
@RestController
@RequestMapping("admin/coupon")
public class                   CouponController {

    @Autowired
    CouponService couponService;

    @GetMapping("list")
    @RequiresPermissions(value={"admin:coupon:list"},logical = Logical.OR)
    public BaseReqVo<CouponListResVo> listCoupon(Integer page, Integer limit, String name, Short type,Short status, String sort, String order){
        CouponListResVo couponListResVo;
        couponListResVo = couponService.queryCoupon(page,limit,name,type,status,sort,order);
        BaseReqVo<CouponListResVo> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(couponListResVo);
        return baseReqVo;
    }

    @PostMapping("create")
    @RequiresPermissions(value={"admin:coupon:create"},logical = Logical.OR)
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
    @RequiresPermissions(value={"admin:coupon:delete"},logical = Logical.OR)
    public BaseReqVo deleteCoupon(@RequestBody Coupon coupon){

        int result = couponService.deleteCoupon(coupon);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @PostMapping("update")
    @RequiresPermissions(value={"admin:coupon:update"},logical = Logical.OR)
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
    @RequiresPermissions(value={"admin:coupon:read"},logical = Logical.OR)
    public BaseReqVo readCoupon(Integer id){
        Coupon coupon = couponService.getCouponById(id);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(coupon);
        return baseReqVo;
    }

    @GetMapping("listuser")
    @RequiresPermissions(value={"admin:coupon:listuser"},logical = Logical.OR)
    public BaseReqVo<CouponUserListResVo> listUserCoupon(Integer page, Integer limit, Integer couponId, Integer userId, Short status, String sort, String order){
        CouponUserListResVo couponUserListResVo;
        couponUserListResVo = couponService.listUserCoupon(page,limit,couponId,userId,status,sort,order);
        BaseReqVo<CouponUserListResVo> baseReqVo = new BaseReqVo<>();
        baseReqVo.setData(couponUserListResVo);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    /*

    这个接口的没有返回参数的格式，需要给到前台才能够继续写
    这个接口位于优惠券->详情->listuser接口（下面那个）


    @GetMapping("listuser")
    public BaseReqVo listuserCoupon(){

    }*/





}
