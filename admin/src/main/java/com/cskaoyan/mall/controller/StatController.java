package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.StatisticUsers;
import com.cskaoyan.mall.service.StatService;
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
    public BaseReqVo<StatisticUsers> viewUser(){
        StatisticUsers statisticUsers = statService.queryStatUser();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(statisticUsers);
        return baseReqVo;
    }
}
