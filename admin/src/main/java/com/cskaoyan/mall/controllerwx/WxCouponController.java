package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.service.CouponService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Author Jerio
 * CreateTime 2019/11/19 20:30
 **/

@RestController
@RequestMapping("wx/coupon")
public class WxCouponController {

    @Autowired
    CouponService couponService;

    @GetMapping("list")
    public BaseReqVo listCoupon(Integer page, Integer size){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map<String,Object> map;
        map = couponService.queryCouponOnWx(page,size);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        return baseReqVo;
    }

    @PostMapping("receive")
    public BaseReqVo receiveCoupon(Integer couponId){
        BaseReqVo baseReqVo = new BaseReqVo();
        int result = couponService.receiveCoupon(couponId);
        //没写完呢，还却个userId来完成这部分的逻辑
        //------------进行user添加券的操作-----

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        System.out.println(user);

        //-------------操作暂未完成--------
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @GetMapping("mylist")
    public BaseReqVo myListCoupon(Integer page, Integer size, Short status){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map<String,Object> map = couponService.myListCoupon(page,size,status);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        return baseReqVo;
    }

    @PostMapping("exchange")
    public BaseReqVo exchangeCoupon(@RequestBody Coupon coupon){
        String code = coupon.getCode();
        BaseReqVo baseReqVo = new BaseReqVo();
        if(code == null){
            baseReqVo.setErrno(763);
            baseReqVo.setErrmsg("兑换码不能为空");
            return baseReqVo;
        }
        int result = couponService.exchangeCouponByCode(code);
        if (result == 1){
            //表示兑换成功
            baseReqVo.setErrno(0);
            baseReqVo.setErrmsg("成功");
        } else {
            //表示优惠券兑换码错误
            baseReqVo.setErrno(742);
            baseReqVo.setErrmsg("优惠券不正确");
        }
        return baseReqVo;
    }
}
