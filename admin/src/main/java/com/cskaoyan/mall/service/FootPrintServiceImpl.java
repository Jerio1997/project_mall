package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Footprint;
import com.cskaoyan.mall.bean.FootprintExample;
import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GoodsExample;
import com.cskaoyan.mall.mapper.FootprintMapper;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class FootPrintServiceImpl implements FootPrintService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    FootprintMapper footprintMapper;
    @Override
    public Map<String, Object> listFootPrint(Integer userId,Integer page, Integer size) {
        FootprintExample footprintExample = new FootprintExample();
        footprintExample.setOrderByClause("add_time"+" "+"DESC");
        footprintExample.createCriteria().andUserIdEqualTo(userId).andDeletedNotEqualTo(true);
        int count = (int) footprintMapper.countByExample(footprintExample);
        int totalPages = ((int) Math.ceil(1.0 * count / size));
        PageHelper.startPage(page,size);
        List<Goods> footprintList = new ArrayList<>();
        List<Footprint> footprints = footprintMapper.selectByExample(footprintExample);
        for (Footprint footprint : footprints) {
            Integer goodsId = footprint.getGoodsId();
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            goods.setAddTime(footprint.getAddTime());
            footprintList.add(goods);
        }
        Map<String,Object> data = new HashMap<>();
        data.put("footprintList",footprintList);
        data.put("totalPages",totalPages);
        return data;
    }

    @Override
    public int addFootPrint(Integer userId, Integer id) {
        Footprint footprint = new Footprint();
        footprint.setAddTime(new Date());
        footprint.setId(null);
        footprint.setDeleted(false);
        footprint.setUserId(userId);
        footprint.setGoodsId(id);
        int insert = footprintMapper.insert(footprint);
        return insert;
    }
}
