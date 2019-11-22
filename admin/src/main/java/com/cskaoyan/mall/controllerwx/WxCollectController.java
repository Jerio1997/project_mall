package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.CollectService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/collect")
public class WxCollectController {
    @Autowired
    CollectService collectService;

    //商品收藏
    @RequestMapping("list")
    public BaseReqVo listCollect(Integer page,Integer type,Integer size){

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer userId = user.getId();

        List<Goods> collectList = collectService.collectList(page,type,size,userId);
        Collect collect = null;
        List<Collect> collects = collectService.collectList1(collect);
        GoodsAndCollect collect1 = new GoodsAndCollect();

        for (Goods goods : collectList) {
            collect1.setBrief(goods.getBrief());
            collect1.setId(goods.getBrandId());
            collect1.setName(goods.getName());
            collect1.setPicUrl(goods.getPicUrl());
            collect1.setRetailPrice(goods.getRetailPrice());
        }
        for (Collect collect2 : collects) {
            collect1.setType(collect2.getType());
            collect1.setValueId(collect2.getValueId());
        }

        PageInfo<Goods> collectPageInfo = new PageInfo<>(collectList);
        int totalPages = collectPageInfo.getPages();

        HashMap<String,Object> data = new HashMap<>();
        data.put("totalPages",totalPages);
        data.put("collectList",collect1);

        //Map<String,Object> data = collectService.getCollectList(page,type,size);

        //List<Collect> data = collectService.collectList(page,type,size);


        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
    //添加或删除商品收藏
    @RequestMapping("addordelete")
    public BaseReqVo addordeleteCollect(@RequestBody Collect collect){

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer userId = user.getId();

        collect.setAddTime(new Date());
        collect.setUpdateTime(new Date());
        //String data = collectService.addordeleteCollect(userId,collect.getValueId());


        HashMap<String, Object> dataMap = collectService.addordeleteCollect(collect,userId);
        //dataMap.put("type", dataMap);

        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(dataMap);
        return baseReqVo;
    }
}
