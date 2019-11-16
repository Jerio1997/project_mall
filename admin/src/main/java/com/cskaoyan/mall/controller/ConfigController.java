package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/config")
public class ConfigController {
    @Autowired
    ConfigService configService;

    @RequestMapping(value="mall",method=RequestMethod.GET)
    public BaseReqVo getMallInfo(){
        BaseReqVo<Mall> baseReqVo = new BaseReqVo<>();
        Mall mallResult = configService.selectMallInfo();
        baseReqVo.setData(mallResult);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping(value="mall",method = RequestMethod.POST)
    public BaseReqVo changeMallInfo(@RequestBody Mall mall){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        int result = configService.updateMallInfo(mall);

        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping(value="express",method = RequestMethod.GET)
    public BaseReqVo getExpressInfo(){
        BaseReqVo<Express> baseReqVo = new BaseReqVo<>();
        Express express = configService.selectExpressInfo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(express);
        return baseReqVo;
    }

    @RequestMapping(value = "express",method = RequestMethod.POST)
    public BaseReqVo changeExpressInfo(@RequestBody Express express){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        int result = configService.updateExpressInfo(express);

        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping(value="order",method = RequestMethod.GET)
    public BaseReqVo getOrderInfo(){
        BaseReqVo<OrderConfig> baseReqVo = new BaseReqVo<>();
        OrderConfig orderConfig = configService.selectOrderInfo();
        baseReqVo.setData(orderConfig);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping(value = "order",method = RequestMethod.POST)
    public BaseReqVo changeOrderInfo(@RequestBody OrderConfig orderConfig){
        BaseReqVo<OrderConfig> baseReqVo = new BaseReqVo<>();
        int result = configService.updateOrderConfigInfo(orderConfig);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping(value = "wx",method = RequestMethod.GET)
    public BaseReqVo getWechatInfo(){
        BaseReqVo<WechatConfig> baseReqVo = new BaseReqVo<>();
        WechatConfig wechatConfig = configService.selectWechatInfo();
        baseReqVo.setData(wechatConfig);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping(value = "wx",method = RequestMethod.POST)
    public BaseReqVo ChangeWechatInfo(@RequestBody WechatConfig wechatConfig){
        BaseReqVo<WechatConfig> baseReqVo = new BaseReqVo<>();
        int result = configService.updateWechatConfigInfo(wechatConfig);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }
}
