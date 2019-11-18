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
import java.util.List;

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
}
