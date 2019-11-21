package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Region;
import com.cskaoyan.mall.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("wx/region")
public class WxRegionController {

    @Autowired
    RegionService regionService;

    @RequestMapping("list")
    public BaseReqVo listRegion(Integer pid) {
        List<Region> regionList = regionService.listRegionByPid(pid);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(regionList);
        return baseReqVo;
    }

}
