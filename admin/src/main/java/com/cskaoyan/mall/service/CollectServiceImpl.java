package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
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
        long totalpages = goodsPageInfo.getTotal();
        Map<String,Object> data = new HashMap<>();
        data.put("totalpages",totalpages);
        data.put("collectList",goodsList);
        return data;
    }

    /*@Override
    public List<Collect> collectList(Integer page, Integer type, Integer size) {
        PageHelper.startPage(page,size);
        GoodsExample goodsExample = new GoodsExample();
        List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
        PageInfo<Goods> goodsPageInfo = new PageInfo<>(goodsList);
        long totalpages = goodsPageInfo.getTotal();
        Map<String,Object> data = new HashMap<>();
        data.put("totalpages",totalpages);
        data.put("collectList",goodsList);
        return goodsList;
    }*/

    @Override
    public HashMap<String, Object> addordeleteCollect(Collect collect,Integer userId) {

        /*collect.setAddTime(new Date());
        collect.setUpdateTime(new Date());*/
//        Collect collect = collectMapper.queryCollect(valueId,userId);
//        if (collect != null){
//            collectMapper.deleteById(valueId,userId);
//            return "delete";
//        }else {
//            collectMapper.insertById(userId,valueId);
//        }
//        return "add";

        HashMap<String, Object> hashHashMap = new HashMap<>();
        CollectExample collectExample = new CollectExample();
        CollectExample.Criteria criteria = collectExample.createCriteria();
        criteria.andValueIdEqualTo(collect.getValueId());
        criteria.andUserIdEqualTo(userId);
        List<Collect> collects = collectMapper.selectByExample(collectExample);
        if (collects.size() == 0){
            collect.setAddTime(new Date());
            collect.setDeleted(false);
            collect.setUpdateTime(new Date());
            collect.setType((byte) 0);
            collect.setUserId(userId);
            collectMapper.insert(collect);
            hashHashMap.put("type","add");
        }else {
            Collect collect1 = collects.get(0);
            if (collect1.getType().intValue() == 0){
                collect1.setType((byte) 1);
                collect1.setUpdateTime(new Date());
                collectMapper.updateByPrimaryKey(collect1);
                hashHashMap.put("type","delete");
            }else {
                collect1.setType((byte) 0);
                collect1.setUpdateTime(new Date());
                collectMapper.updateByPrimaryKey(collect1);
                hashHashMap.put("type","add");
            }
        }
        return hashHashMap;
    }

    /*@Override
    public String addordeleteCollect(Integer valueId) {
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
    }*/
}
