package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.service.WxUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("wx/user")
public class WxUserController {

    @Autowired
    WxUserService wxUserService;
    @RequestMapping("index")
    public BaseReqVo userIndex(){
        Map<String,Object> data = wxUserService.userIndex();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
}
