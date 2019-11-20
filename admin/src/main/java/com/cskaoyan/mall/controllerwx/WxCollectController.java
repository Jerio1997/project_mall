package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("wx/collect")
public class WxCollectController {
    @Autowired
    CollectService collectService;

    //商品收藏
    @RequestMapping("list")
    public BaseReqVo listCollect(Integer page,Integer type,Integer size){
        Map<String,Object> data = collectService.getCollectList(page,type,size);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
    //添加或删除商品收藏
    @RequestMapping("addordelete")
    public BaseReqVo addordeleteCollect(Byte type,Integer valueId){
        Map<String,Object> data = collectService.addordeleteCollect(type,valueId);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
}
