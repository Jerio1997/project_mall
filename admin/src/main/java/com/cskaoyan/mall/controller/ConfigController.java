package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Mall;
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
    public BaseReqVo mall(@RequestBody Mall mall){
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        configService.updateMallSystem(mall);

    }
}
