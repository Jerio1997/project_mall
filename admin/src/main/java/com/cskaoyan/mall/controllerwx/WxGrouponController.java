package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.service.GrouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Author Jerio
 * CreateTime 2019/11/20 15:05
 **/

@RestController
@RequestMapping("wx/groupon")
public class WxGrouponController {

    @Autowired
    GrouponService grouponService;

    @RequestMapping("list")
    public BaseReqVo listGroupon(Integer page,Integer size){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map<String,Object> map = grouponService.listGroupon(page,size);
        baseReqVo.setData(map);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    
}
