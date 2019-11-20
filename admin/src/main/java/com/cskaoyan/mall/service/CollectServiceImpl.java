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
public class CollectServiceImpl implements CollectService {

    @Autowired
    GoodsMapper goodsMapper;
    @Override
    public Map<String, Object> getCollectList(Integer page, Integer type, Integer size) {
        PageHelper.startPage(page,size);
        GoodsExample goodsExample = new GoodsExample();
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        PageInfo<Goods> goodsPageInfo = new PageInfo<>(goodsList);
        long totalpages = goodsPageInfo.getPages();
        Map<String,Object> data = new HashMap<>();
        data.put("totalpages",totalpages);
        data.put("collectList",goodsList);
        return data;
    }

    @Override
    public Map<String, Object> addordeleteCollect(Byte type, Integer valueId) {
        return null;
    }
}
