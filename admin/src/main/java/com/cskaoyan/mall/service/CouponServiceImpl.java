package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Coupon;
import com.cskaoyan.mall.bean.CouponExample;
import com.cskaoyan.mall.mapper.CouponMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 11:41
 **/
@Service
public class CouponServiceImpl implements CouponService{

    @Autowired
    CouponMapper couponMapper;

    @Override
    public int queryCouponCounts() {
        CouponExample couponExample = new CouponExample();
        long total = couponMapper.countByExample(couponExample);
        return (int) total;
    }

    @Override
    public List<Coupon> queryCoupon(Integer page, Integer limit, String name, Short type, Short status, String sort, String order) {
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        CouponExample couponExample = new CouponExample();
        couponExample.setOrderByClause(orderByClause);
        CouponExample.Criteria criteria = couponExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        if(!StringUtils.isEmpty(type)){
            criteria.andTypeEqualTo(type);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        //这里如何加一个判断deleted
        List<Coupon> coupons = couponMapper.selectByExample(couponExample);
        return coupons;
    }

    @Override
    public int createCoupon(Coupon coupon) {
        CouponExample example = new CouponExample();
        example.setOrderByClause("id desc");
        List<Coupon> couponList = couponMapper.selectByExample(example);
        Integer id;
        if(couponList == null || couponList.size() == 0){
            id = 0;
        } else {
            id = couponList.get(0).getId();
        }
        coupon.setId(++id);
        int result = couponMapper.insertSelective(coupon);
        return result;
    }

    @Override
    public int deleteCoupon(Coupon coupon) {
        coupon.setDeleted(true);
//        coupon.setUpdateTime(new Date());
        //删除时要不要更新时间？
        int result = couponMapper.updateByPrimaryKey(coupon);
        return result;
    }

    @Override
    public int updateCoupon(Coupon coupon) {
        coupon.setUpdateTime(new Date());
        int result = couponMapper.updateByPrimaryKey(coupon);
        return result;
    }

    @Override
    public Coupon getCouponById(Integer id) {
        Coupon coupon = couponMapper.selectByPrimaryKey(id);
        return coupon;
    }

}
