package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Mall;
import com.cskaoyan.mall.bean.System;
import com.cskaoyan.mall.bean.SystemExample;
import com.cskaoyan.mall.mapper.SystemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    SystemMapper systemMapper;


    @Override
    public Mall selectMallInfo() {
        Mall mall = new Mall();

        System mallName = systemMapper.selectByPrimaryKey(6);
        mall.setLitemall_mall_name(mallName.getKeyValue());

        System mallQQ = systemMapper.selectByPrimaryKey(8);
        mall.setLitemall_mall_qq(mallQQ.getKeyValue());

        System mallPhone = systemMapper.selectByPrimaryKey(12);
        mall.setLitemall_mall_phone(mallPhone.getKeyValue());

        System mallAddress = systemMapper.selectByPrimaryKey(14);
        mall.setLitemall_mall_address(mallAddress.getKeyValue());

        return mall;
    }

    @Override
    public int updateMallInfo(Mall mall) {
        Date date = new Date();
        System system = new System();

        String mallName = mall.getLitemall_mall_name();

        String mallPhone = mall.getLitemall_mall_phone();

        String mallQQ = mall.getLitemall_mall_qq();

        String mallAddress = mall.getLitemall_mall_address();


        SystemExample mallNameExample = new SystemExample();
        mallNameExample.createCriteria().andIdEqualTo(6);
        system.setKeyValue(mallName);
        system.setUpdateTime(date);


        if (!mallName.equals("")) {
            systemMapper.updateByExampleSelective(system, mallNameExample);
        }


        SystemExample mallPhoneExample = new SystemExample();
        mallPhoneExample.createCriteria().andIdEqualTo(12);
        system.setKeyValue(mallPhone);

        if (!mallPhone.equals("")) {

            systemMapper.updateByExampleSelective(system, mallPhoneExample);
        }

        SystemExample mallQQExample = new SystemExample();
        mallQQExample.createCriteria().andIdEqualTo(8);
        system.setKeyValue(mallQQ);

        if (!mallQQ.equals("")) {

            systemMapper.updateByExampleSelective(system, mallQQExample);
        }

        SystemExample mallAddressExample = new SystemExample();
        mallAddressExample.createCriteria().andIdEqualTo(14);
        system.setKeyValue(mallAddress);

        if (!mallAddress.equals("")) {

            systemMapper.updateByExampleSelective(system, mallAddressExample);
        }

        return 0;
    }
}
