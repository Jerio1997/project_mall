package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.CouponMapper;
import com.cskaoyan.mall.mapper.CouponUserMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Author Jerio
 * CreateTime 2019/11/16 11:41
 **/
@Service
public class CouponServiceImpl implements CouponService{

    @Autowired
    CouponMapper couponMapper;

    @Autowired
    CouponUserMapper couponUserMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public int queryCouponCounts() {
        CouponExample couponExample = new CouponExample();
        long total = couponMapper.countByExample(couponExample);
        return (int) total;
    }

    @Override
    public CouponListResVo queryCoupon(Integer page, Integer limit, String name, Short type, Short status, String sort, String order) {
        CouponListResVo couponListResVo = new CouponListResVo();
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
        List<Coupon> couponList = couponMapper.selectByExample(couponExample);
        couponListResVo.setItems(couponList);
        couponListResVo.setTotal(couponList.size());
        return couponListResVo;
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



    @Override
    public CouponUserListResVo listUserCoupon(Integer page, Integer limit, Integer couponId, Integer userId, Short status, String sort, String order) {
        CouponUserListResVo couponUserListResVo = new CouponUserListResVo();
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
//        CouponExample example = new CouponExample();
        CouponUserExample example = new CouponUserExample();
        example.setOrderByClause(orderByClause);
        CouponUserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(couponId);
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(status)){
            criteria.andStatusEqualTo(status);
        }
        List<CouponUser> couponUserList = couponUserMapper.selectByExample(example);
        int total = couponUserList.size();
        couponUserListResVo.setTotal(total);
        couponUserListResVo.setItems(couponUserList);
        return couponUserListResVo;
    }

    @Override
    public Map<String, Object> queryCouponOnWx(Integer page, Integer size) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(page,size);
        CouponExample example = new CouponExample();
        CouponExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Coupon> couponList = couponMapper.selectByExample(example);
        map.put("data",couponList);
        map.put("count",couponList.size());
        return map;
    }

    @Override
    @Transactional
    public int receiveCoupon(Integer couponId) {
        //这里要写获得优惠券的相关sql操作以及逻辑判断
        //不行，我还得要个UserId
        return 0;
    }

    @Override
    public Map<String, Object> myListCoupon(Integer page, Integer size, Short status) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(page,size);
        CouponExample example = new CouponExample();
        CouponExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andStatusEqualTo(status);
        List<Coupon> couponList = couponMapper.selectByExample(example);
        map.put("data",couponList);
        map.put("count",couponList.size());
        return map;
    }

    /**
     * 兑换优惠券
     * @param code
     * @return  1:表示兑换成功    2:表示优惠券不正确
     */
    @Override
    @Transactional
    public int exchangeCouponByCode(String code) {//还缺个userId
        CouponExample example = new CouponExample();
        CouponExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        short id = 0;
        criteria.andStatusEqualTo(id);
        criteria.andCodeEqualTo(code);
        List<Coupon> couponList = couponMapper.selectByExample(example);
        if(couponList.size() == 0){
            //表示没查到
            return 2;
        }
        for (Coupon coupon : couponList) {
            //-------先对优惠券的数量的操作------------
            //能够查到的都是未删除的、兑换码正确、券状态为可用的 券
            Coupon couponCur = new Coupon();
            couponCur.setUpdateTime(new Date());
            if(coupon.getTotal() == 1){
                //表示只剩下1张券了，要把它删除状态置位
                couponCur.setDeleted(true);
            } else {
                //表示数量还够,更改数量
                couponCur.setTotal((coupon.getTotal()-1));
            }
            couponMapper.updateByPrimaryKeySelective(couponCur);
            //--------再对user添加相应的优惠券------------
            /*此处还要个userId*/

        }
        return 1;
    }


}
