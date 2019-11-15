package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Region;
import com.cskaoyan.mall.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/region")
public class RegionController {

    @Autowired
    RegionService regionService;

    /**
     * 查询全国的行政区域
     * @return
     */
    @RequestMapping("list")
    public BaseReqVo listRegion() {
        BaseReqVo baseReqVo = new BaseReqVo();
        List<Region> regionList = regionService.listRegion();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(regionList);
        return baseReqVo;
    }

}
