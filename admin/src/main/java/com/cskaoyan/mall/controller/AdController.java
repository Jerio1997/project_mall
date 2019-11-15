package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.Ad;
import com.cskaoyan.mall.bean.AdListResVo;
import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        BaseReqVo<AdListResVo> adListResVoBaseReqVo = new BaseReqVo<>();
        int total = adService.queryAdCounts();
        List<Ad> ads = adService.queryAd(page,limit,name,content,sort,order);
        AdListResVo adListResVo = new AdListResVo();
        adListResVo.setItems(ads);
        adListResVo.setTotal(total);
        adListResVoBaseReqVo.setErrno(0);
        adListResVoBaseReqVo.setErrmsg("成功");
        adListResVoBaseReqVo.setData(adListResVo);
        return adListResVoBaseReqVo;

    }

}
