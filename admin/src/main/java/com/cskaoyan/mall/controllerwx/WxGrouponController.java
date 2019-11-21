package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.service.GrouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
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

    @GetMapping("list")
    public BaseReqVo listGroupon(Integer page,Integer size){
        BaseReqVo<Map> baseReqVo = new BaseReqVo<>();
        Map<String,Object> map = grouponService.listGroupon(page,size);
        baseReqVo.setData(map);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @GetMapping("my")
    /*没写完！缺userId*/
    public BaseReqVo myGroupon(Integer showType){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map<String,Object> map = grouponService.queryMyGroupon(showType);
        baseReqVo.setData(map);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @GetMapping("detail")
    public BaseReqVo detailGroupon(Integer grouponId){
        BaseReqVo<Map> baseReqVo = new BaseReqVo<>();
        Map<String,Object> map = grouponService.getDetailOfGrouponById(grouponId);
        baseReqVo.setData(map);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }
}
