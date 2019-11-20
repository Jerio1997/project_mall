package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GoodsExample;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FootPrintServiceImpl implements FootPrintService {
    @Autowired
    GoodsMapper goodsMapper;
    @Override
    public Map<String, Object> listFootPrint(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        GoodsExample goodsExample = new GoodsExample();
        List<Goods> footprintList = goodsMapper.selectByExample(goodsExample);
        Map<String,Object> data = new HashMap<>();
        data.put("footprintList",footprintList);
        return data;
    }
}
