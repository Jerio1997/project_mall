package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.Ad;
import com.cskaoyan.mall.bean.AdListResVo;
import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.service.AdService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/15 21:37
 **/
@RestController
@RequestMapping("admin/ad")
public class AdController {

    @Autowired
    AdService adService;

    @GetMapping("list")
    public BaseReqVo<AdListResVo> listAd(Integer page, Integer limit, String name, String content, String sort, String order){
        AdListResVo adListResVo;
        adListResVo = adService.queryListAd(page,limit,name,content,sort,order);
        BaseReqVo<AdListResVo> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(adListResVo);
        return baseReqVo;
    }

    @PostMapping("create")
    public BaseReqVo<Ad> createAd(@RequestBody Ad ad){
        ad.setAddTime(new Date());
        ad.setUpdateTime(new Date());
        int result = adService.createAd(ad);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setData(ad);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;

    }

    @PostMapping("update")
    public BaseReqVo updateAd(@RequestBody Ad ad){
        ad.setUpdateTime(new Date());
        int result = adService.updateAd(ad);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setData(ad);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @PostMapping("delete")
    public BaseReqVo deleteAd(@RequestBody Ad ad){
        int result = adService.deleteAd(ad);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }






}
