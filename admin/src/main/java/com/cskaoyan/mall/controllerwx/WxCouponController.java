package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.CouponService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
        BaseReqVo<Map> baseReqVo = new BaseReqVo<>();
        Map<String,Object> map;
        map = couponService.queryCouponOnWx(page,size);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(map);
        return baseReqVo;
    }

    //@return  -2:券已经过期  -3:当前券领取数量已经达到上限  0:没啥问题
    @PostMapping("receive")
    public BaseReqVo receiveCoupon(@RequestBody CurCoupon coupon){
        BaseReqVo baseReqVo = new BaseReqVo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer userId = user.getId();
        Integer id = coupon.getCouponId();
        int result = couponService.receiveCoupon(id,userId);
        if(result == 0) {
            baseReqVo.setErrno(0);
            baseReqVo.setErrmsg("成功");
            return baseReqVo;
        }else if(result == -2){
            baseReqVo.setErrmsg("券已过期");
        }else if(result == -3){
            baseReqVo.setErrmsg("没啊");
        }
        baseReqVo.setErrno(737);
        return baseReqVo;
    }

    @GetMapping("mylist")
    public BaseReqVo myListCoupon(Integer page, Integer size, Short status){
        BaseReqVo<Map> baseReqVo = new BaseReqVo<>();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Map<String,Object> map = couponService.myListCoupon(page,size,status,user.getId());
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
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        int result = couponService.exchangeCouponByCode(code,user.getId());
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

    @GetMapping("selectlist")
    public BaseReqVo selectListOfCoupon(Integer cartId, Integer grouponRulesId){
        BaseReqVo<List> baseReqVo = new BaseReqVo<>();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Coupon> couponList = couponService.selectList(cartId,grouponRulesId,user.getId());
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(couponList);
        return baseReqVo;
    }
}
