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
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        return baseReqVo;
    }
}
