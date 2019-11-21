package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.service.FootPrintService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("wx/footprint")
public class WxFootPrintController {
    @Autowired
    FootPrintService footPrintService;

    //浏览足迹
    @RequestMapping("list")
    public BaseReqVo listFootPrint(Integer page,Integer size){
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        Integer userId = principal.getId();
        Map<String,Object> data = footPrintService.listFootPrint(userId,page,size);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
}
