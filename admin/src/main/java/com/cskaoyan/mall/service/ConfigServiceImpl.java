package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Mall;
import com.cskaoyan.mall.bean.System;
import com.cskaoyan.mall.bean.SystemExample;
import com.cskaoyan.mall.mapper.SystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    SystemMapper systemMapper;
    @Override
    public void updateMallSystem(Mall mall) {
        SystemExample examlpe1 = new SystemExample();
        examlpe1.createCriteria().andKeyNameEqualTo(mall.getLitemallMallName());
        List<System> systems = systemMapper.selectByExample(examlpe1);
    }
}
