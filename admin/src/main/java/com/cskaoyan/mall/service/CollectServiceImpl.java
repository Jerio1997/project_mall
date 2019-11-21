package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Collect;
import com.cskaoyan.mall.bean.CollectExample;
import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GoodsExample;
import com.cskaoyan.mall.mapper.CollectMapper;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    CollectMapper collectMapper;
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
    public String addordeleteCollect(Integer userId, Integer valueId) {
        Collect collect = new Collect();
        collect.setAddTime(new Date());
        collect.setUpdateTime(new Date());
        collect = collectMapper.queryCollect(valueId,userId);
        if (collect != null){
            collectMapper.deleteById(valueId,userId);
            return "delete";
        }else {
            collectMapper.insertById(userId,valueId);
        }
        return "add";
    }
}
