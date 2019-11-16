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

    }

    @Override
    public Mall selectMallInfo() {
        Mall mall = new Mall();

        System mallName = systemMapper.selectByPrimaryKey(6);
        mall.setLitemall_mall_name(mallName.getKeyName());

        System mallQQ = systemMapper.selectByPrimaryKey(8);
        mall.setLitemall_mall_qq(mallQQ.getKeyValue());

        System mallPhone = systemMapper.selectByPrimaryKey(12);
        mall.setLitemall_mall_phone(mallPhone.getKeyValue());

        System mallAddress = systemMapper.selectByPrimaryKey(14);
        mall.setLitemall_mall_address(mallAddress.getKeyValue());

        return mall;
    }
}
