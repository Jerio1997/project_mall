package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Region;

import java.util.List;

public interface RegionService {
    List<Region> listRegion();

    List<Region> listRegionByPid(Integer pid);
}
