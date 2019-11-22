package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.GoodsStatVo;
import com.cskaoyan.mall.bean.OrderStatResVo;
import com.cskaoyan.mall.bean.StatisticUsers;
import com.cskaoyan.mall.service.StatService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author Jerio
 * CreateTime 2019/11/17 23:22
 **/
@RestController
@RequestMapping("admin/stat")
public class StatController {

    @Autowired
    StatService statService;

    @GetMapping("user")
    @RequiresPermissions(value={"admin:stat:user"},logical = Logical.OR)
    public BaseReqVo<StatisticUsers> viewUser() {
        StatisticUsers statisticUsers = statService.queryStatUser();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(statisticUsers);
        return baseReqVo;
    }

    @RequestMapping("goods")
    @RequiresPermissions(value={"admin:stat:goods"},logical = Logical.OR)
    public GoodsStatVo viewGoods() {
        GoodsStatVo goodsStatVo = statService.queryStatGoods();
        goodsStatVo.setErrno(0);
        goodsStatVo.setErrmsg("成功");
        return goodsStatVo;
    }

    @RequestMapping("order")
    @RequiresPermissions(value={"admin:stat:order"},logical = Logical.OR)
    public OrderStatResVo viewOrder() {
        OrderStatResVo orderStatResVo = statService.queryStatOrder();
        orderStatResVo.setErrno(0);
        orderStatResVo.setErrmsg("成功");
        return orderStatResVo;
    }
}
