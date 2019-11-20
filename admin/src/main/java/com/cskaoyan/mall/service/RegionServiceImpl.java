package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Region;
import com.cskaoyan.mall.bean.RegionExample;
import com.cskaoyan.mall.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    RegionMapper regionMapper;

    @Override
    public List<Region> listRegion() {
        RegionExample example = new RegionExample();
        example.createCriteria().andPidEqualTo(0); // pid=0 代表该行政级别为省
        List<Region> regionList = regionMapper.selectByExample(example);
        for (Region region : regionList) { // 通过region的 id 查询其下一级行政单位的列表（省级 查询 其下属 市级）
            List<Region> regionList1;
            RegionExample example1 = new RegionExample();
            example1.createCriteria().andPidEqualTo(region.getId());
            regionList1 = regionMapper.selectByExample(example1);
            for (Region region1 : regionList1) { // 通过region1的 id 查询其下一级行政单位的列表（市级 查询 其下属 区级）
                List<Region> regionList2;
                RegionExample example2 = new RegionExample();
                example2.createCriteria().andPidEqualTo(region1.getId());
                regionList2 = regionMapper.selectByExample(example2);
                region1.setChildren(regionList2);
            }
            region.setChildren(regionList1);
        }
        return regionList;
    }

    @Override
    public List<Region> listRegionByPid(Integer pid) {
        RegionExample regionExample = new RegionExample();
        regionExample.createCriteria().andPidEqualTo(pid);
        List<Region> regionList = regionMapper.selectByExample(regionExample);
        return regionList;
    }
}
