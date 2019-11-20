package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.mapper.GrouponMapper;
import com.cskaoyan.mall.mapper.GrouponRulesMapper;
import com.cskaoyan.mall.mapper.OrderGoodsMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author Jerio
 * CreateTime 2019/11/16 16:21
 **/

@Service
public class GrouponServiceImpl implements GrouponService{

    @Autowired
    GrouponMapper grouponMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Autowired
    OrderGoodsMapper orderGoodsMapper;

    @Override
    public GrouponRecordListResVo getGrouponRecord(Integer page, Integer limit, Integer goodsId, String sort, String order) {
        GrouponRecordListResVo grouponRecordListResVo = new GrouponRecordListResVo();
        List<GrouponRecord> grouponRecordList = new ArrayList<>();
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        GrouponExample grouponExample = new GrouponExample();
        grouponExample.setOrderByClause(orderByClause);
        GrouponExample.Criteria criteria = grouponExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Groupon> grouponList = grouponMapper.selectByExample(grouponExample);
        int total = grouponList.size();
        for (Groupon groupon : grouponList) {
            GrouponRecord grouponRecord = new GrouponRecord();
            grouponRecord.setGroupon(groupon);
            grouponRecord.setRules(grouponRulesMapper.selectByPrimaryKey(groupon.getRulesId()));
            criteria.andGrouponIdEqualTo(groupon.getId());
            List<Groupon> subGroupons = grouponMapper.selectByExample(grouponExample);
            grouponRecord.setSubGroupons(subGroupons);
            OrderGoods orderGoods = orderGoodsMapper.selectByOrderId(groupon.getOrderId());
            Goods goods = goodsMapper.selectByPrimaryKey(orderGoods.getGoodsId());
            grouponRecord.setGoods(goods);
            grouponRecordList.add(grouponRecord);
        }
        grouponRecordListResVo.setItems(grouponRecordList);
        grouponRecordListResVo.setTotal(total);
        return grouponRecordListResVo;
    }

    @Override
    public Map<String, Object> listGroupon(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        GrouponExample example = new GrouponExample();
        GrouponExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Groupon> grouponList = grouponMapper.selectByExample(example);
        List list = new ArrayList();
        for (Groupon groupon : grouponList) {
            Map<String,Object> mapCur = new HashMap<>();
            GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(groupon.getRulesId());
            mapCur.put("groupon_price",grouponRules.getDiscount());
            Goods goods = goodsMapper.selectByPrimaryKey(grouponRules.getGoodsId());
            mapCur.put("goods",goods);
            mapCur.put("groupon_member",grouponRules.getDiscountMember());
            list.add(mapCur);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("data",list);
        map.put("count",list.size());
        return map;
    }
}
