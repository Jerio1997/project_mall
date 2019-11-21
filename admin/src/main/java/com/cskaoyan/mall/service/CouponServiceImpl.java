package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.CartMapper;
import com.cskaoyan.mall.mapper.CouponMapper;
import com.cskaoyan.mall.mapper.CouponUserMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
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

    @Autowired
    CartMapper cartMapper;

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
        Date date = new Date();
        coupon.setAddTime(date);
        coupon.setUpdateTime(date);
        //判断是否过期
        if(coupon.getEndTime() != null){
            if(date.after(coupon.getEndTime())){
                coupon.setStatus((short) 1);
            }
        }
        //设置结束时间
        if(coupon.getTimeType() == 0){
            coupon.setStartTime(coupon.getAddTime());
            Date addtime = coupon.getAddTime();
            long time = addtime.getTime();
            long day = coupon.getDays();
            time += 24*60*60*day;
            Date date1 = new Date();
            date1.setTime(time);
            coupon.setEndTime(date1);
        }

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
        //设置已经逻辑删除的不可见
        criteria.andDeletedEqualTo(false);
        //设置兑换码券不可见
        criteria.andTypeNotEqualTo((short) 2);
        //设置过期的不可见
        criteria.andStatusNotEqualTo((short) 1);
        List<Coupon> couponList = couponMapper.selectByExample(example);
        map.put("data",couponList);
        map.put("count",couponList.size());
        return map;
    }

    /**
     * 获得优惠券
     * @param couponId
     * @param userId
     * @return  -2:券已经过期  -3:当前券领取数量已经达到上限  0:没啥问题
     */
    @Override
    @Transactional
    public int receiveCoupon(Integer couponId,Integer userId) {
        //这里要写获得优惠券的相关sql操作以及逻辑判断
        //---------优惠券操作-----------
        //      --优惠券列表仅显示注册用券和通用券--
        Date date = new Date();
        Coupon couponA = couponMapper.selectByPrimaryKey(couponId);
        Coupon coupon = judgeCoupon(couponA);
        //若是每人限领张数，则先判断
        if(coupon.getLimit() != 0){
            //表示限制领券张数，先查询当前用户已有数量
            CouponUserExample example = new CouponUserExample();
            CouponUserExample.Criteria criteria = example.createCriteria();
            criteria.andCouponIdEqualTo(couponId);
            criteria.andUserIdEqualTo(userId);
            List<CouponUser> couponUsers = couponUserMapper.selectByExample(example);
            if((couponUsers.size() >= coupon.getLimit())){
                //表示不能接着领取
                return -3;
            }
        }
        if(coupon.getStatus() == 1){
            //表示过期
            return -2;
        }
        int coupon_num = coupon.getTotal();
        if(coupon_num != 0){
            //表示是有数量的券
            if(coupon_num > 1){
                coupon.setTotal(--coupon_num);
            } else {
                coupon.setDeleted(true);
            }
            coupon.setUpdateTime(new Date());
        }

        couponMapper.updateByPrimaryKeySelective(coupon);
//        couponMapper.updateStausAndTimeById(coupon.getId());


        //----------coupon_user相关insert操作-----------
        CouponUser couponUser = new CouponUser();
        //获得上一个的id
        CouponUserExample example = new CouponUserExample();
        example.setOrderByClause("id desc");
        List<CouponUser> couponUsers = couponUserMapper.selectByExample(example);
        Integer id;
        if(couponUsers == null || couponUsers.size() == 0){
            id = 0;
        } else {
            id = couponUsers.get(0).getId();
        }
//        couponUser.setId(++id);
        couponUser.setId(null);
        //再其他信息
        couponUser.setUserId(userId);
        couponUser.setCouponId(couponId);
        //coupon三状态         0:可用  1:过期 2:下架
        //coupon_user三状态    0:可用  1:已用 2:过期  3:下架
        if(coupon.getStatus() != 0){
            couponUser.setStatus((short) (coupon.getStatus()+1));
        } else { couponUser.setStatus((short) 0); }
        if(coupon.getTimeType() == 1) {
            couponUser.setStartTime(coupon.getStartTime());
        } else {
            couponUser.setStartTime(coupon.getAddTime());
        }
        couponUser.setEndTime(coupon.getEndTime());
        couponUser.setAddTime(date);
        couponUser.setUpdateTime(date);
        couponUser.setDeleted(false);
        couponUserMapper.insert(couponUser);
        return 0;
    }

    /**
     * 判断优惠券是否日期可用
     * @param coupon
     * @return
     */
    private Coupon judgeCoupon(Coupon coupon) {
        Date date = new Date();
        long curTime = date.getTime();
        if(coupon.getTimeType() == 0){
            //表示是天数券
            Date addtime = coupon.getAddTime();
            long time = addtime.getTime();
            long day = coupon.getDays();
            time += 24*60*60*day;
            Date date1 = new Date();
            date1.setTime(time);
            coupon.setEndTime(date1);
            /*Date date1 = coupon.getEndTime();
            long time = date1.getTime();*/
            if(curTime < time){
                //表示没有过期
                coupon.setStatus((short) 0);
                return coupon;
            }
            //剩下的就是过期了
            coupon.setStatus((short) 1);
            return coupon;
        } else {
            //表示是日期券
            if(date.before(coupon.getStartTime())){
                //表示还没到可用日期，先下架
                coupon.setStatus((short) 0);
                return coupon;
            }
            if(date.before(coupon.getEndTime())){
                //表示还没过期
                coupon.setStatus((short) 0);
                return coupon;
            }
            //剩余的就是已经过期了
            coupon.setStatus((short) 1);
            return coupon;
        }
    }

    @Override
    @Transactional
    public Map<String, Object> myListCoupon(Integer page, Integer size, Short status, Integer userId) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(page,size);
        /*CouponExample example = new CouponExample();
        CouponExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andStatusEqualTo(status);
        List<Coupon> couponList = couponMapper.selectByExample(example);
        map.put("data",couponList);
        map.put("count",couponList.size());*/
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(status != 0 ){ status = 2;}//因为status在两表中意义不同：  coupon_user->coupon      0->0    2->1
        criteria.andStatusEqualTo(status);
        criteria.andUserIdEqualTo(userId);
        List<CouponUser> couponUserList = couponUserMapper.selectByExample(example);
        List<Coupon> couponList = new ArrayList<>();
        for (CouponUser couponUser : couponUserList) {
            //对每一个couponUser,找到相应的coupon
            Coupon coupona = couponMapper.selectByPrimaryKey(couponUser.getCouponId());
            Coupon coupon = judgeCoupon(coupona);
            //要不要更新,更！
            couponMapper.updateByPrimaryKeySelective(coupon);
            if(!coupon.getDeleted()) {
                couponList.add(coupon);
            }
        }
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
    public int exchangeCouponByCode(String code, Integer userId) {//还缺个userId  补上了
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
            Coupon coupona = judgeCoupon(coupon);
            couponList.remove(coupon);
            couponList.add(coupona);
        }

        for (Coupon coupon : couponList) {
            //-------先对优惠券的数量的操作------------
            //能够查到的都是未删除的、兑换码正确、券日期可用、状态status为可用的 券
            Coupon couponCur = new Coupon();
            couponCur.setUpdateTime(new Date());
            couponCur.setId(coupon.getId());
            if(coupon.getTotal() == 1){
                //表示只剩下1张券了，要把它删除状态置位
                couponCur.setDeleted(true);
            } else {
                //表示数量还够,更改数量
                couponCur.setTotal((coupon.getTotal()-1));
            }
            couponMapper.updateByPrimaryKeySelective(couponCur);
            //--------再对user添加相应的优惠券------------
            /*此处还要个userId               有了*/
            CouponUser couponUser = new CouponUser();
            CouponUserExample example2 = new CouponUserExample();
            example.setOrderByClause("id desc");
            List<CouponUser> couponUsers = couponUserMapper.selectByExample(example2);
            Integer cu_id;
            if(couponUsers == null || couponUsers.size() == 0){
                cu_id = 0;
            } else {
                cu_id = couponUsers.get(0).getId();
            }
            couponUser.setId(cu_id);
            couponUser.setUserId(userId);
            couponUser.setCouponId(coupon.getId());
            couponUser.setStartTime(coupon.getStartTime());
            couponUser.setEndTime(coupon.getEndTime());
            couponUser.setAddTime(new Date());
            couponUser.setUpdateTime(new Date());
            couponUser.setDeleted(false);
            couponUserMapper.insert(couponUser);
        }
        return 1;
    }

    @Override
    @Transactional
    public List<Coupon> selectList(Integer cartId, Integer grouponRulesId, Integer userId) {
        CouponUserExample example = new CouponUserExample();
        CouponUserExample.Criteria criteria = example.createCriteria();
        //coupon_user三状态    0:可用  1:已用 2:过期  3:下架
        criteria.andStatusEqualTo((short) 0);
        criteria.andUserIdEqualTo(userId);
        criteria.andStartTimeLessThan(new Date());
        criteria.andEndTimeGreaterThan(new Date());
        List<CouponUser> couponUserList = couponUserMapper.selectByExample(example);
        List<Coupon> couponList = new ArrayList<>();
        for (CouponUser couponUser : couponUserList) {
            Coupon coupon = couponMapper.selectByPrimaryKey(couponUser.getCouponId());
            couponList.add(coupon);
        }
        //获得订单总价，以此来判断能否用优惠券
        //cartId 0:所有checkid              其他:具体的一条cart
        BigDecimal totalPrice = new BigDecimal(0);
        if(cartId == 0) {//表示不止一条cart
            CartExample example1 = new CartExample();
            CartExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andCheckedEqualTo(true);//表示选中的cart
            criteria1.andDeletedEqualTo(false);
            List<Cart> cartList = cartMapper.selectByExample(example1);
            for (Cart cart : cartList) {
                BigDecimal number_bigDecimal = new BigDecimal(cart.getNumber());
                BigDecimal price = cart.getPrice().multiply(number_bigDecimal);
                totalPrice = totalPrice.add(price);
            }
        } else {
            //表示只有一条cart
            Cart cart = cartMapper.selectByPrimaryKey(cartId);
            BigDecimal number_bigDecimal = new BigDecimal(cart.getNumber());
            totalPrice = totalPrice.add(cart.getPrice().multiply(number_bigDecimal));
        }
        //得到满足couponUser要求的couponlist，逐一遍历，判断是否满足购物总价
        for (Coupon coupon : couponList) {
            BigDecimal min = coupon.getMin();
            if(totalPrice.compareTo(min) < 0 ){
                couponList.remove(coupon);
            }
        }
        //满足
        return couponList;
    }


}
