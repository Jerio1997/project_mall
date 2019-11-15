package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;

    @RequestMapping("dashboard")
    public BaseReqVo dashboard(){
        BaseReqVo<Map> baseReqVo = new BaseReqVo<>();
        Long userTotal = dashboardService.selectUserCount();
        Long goodsTotal = dashboardService.selectGoodsCount();
        Long orderTotal = dashboardService.selectOrdersCount();
        Long productTotal = dashboardService.selectProductsCount();
        Map<String, Long> data = new HashMap<>();
        data.put("goodsTotal",goodsTotal);
        data.put("userTotal",userTotal);
        data.put("orderTotal",orderTotal);
        data.put("productTotal",productTotal);
        baseReqVo.setData(data);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
