package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GrouponRules;
import com.cskaoyan.mall.bean.GrouponRulesExample;
import com.cskaoyan.mall.bean.GrouponRulesListResVo;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.cskaoyan.mall.mapper.GrouponRulesMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 17:56
 **/
@Service
public class GrouponRulesServiceImpl implements GrouponRulesService{

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Override
    public int queryGrouponRulesCounts() {
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        long total = grouponRulesMapper.countByExample(grouponRulesExample);
        return (int) total;
    }

    @Override
    public GrouponRulesListResVo queryGrouponRules(Integer page, Integer limit, Integer goodsId, String sort, String order) {
        GrouponRulesListResVo grouponRulesListResVo = new GrouponRulesListResVo();
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        grouponRulesExample.setOrderByClause(orderByClause);
        GrouponRulesExample.Criteria criteria = grouponRulesExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(goodsId)){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(grouponRulesExample);
        grouponRulesListResVo.setItems(grouponRules);
        grouponRulesListResVo.setTotal(grouponRules.size());
        return grouponRulesListResVo;
    }

    @Override
    public int createGrouponRules(GrouponRules grouponRules) {
        GrouponRulesExample example = new GrouponRulesExample();
        example.setOrderByClause("id desc");
        List<GrouponRules> grouponRulesList = grouponRulesMapper.selectByExample(example);
        Integer id;
        if(grouponRulesList == null || grouponRulesList.size() == 0){
            id = 0;
        } else {
            id = grouponRulesList.get(0).getId();
        }
        grouponRules.setId(++id);
        Goods goods = goodsMapper.selectByPrimaryKey(grouponRules.getGoodsId());
        grouponRules.setGoodsName(goods.getName());
        grouponRules.setPicUrl(goods.getPicUrl());
        grouponRules.setAddTime(new Date());
        grouponRules.setUpdateTime(new Date());

        int result = grouponRulesMapper.insertSelective(grouponRules);
        return result;
    }

    @Override
    public int updateGrouponRules(GrouponRules grouponRules) {
        grouponRules.setUpdateTime(new Date());
        int result = grouponRulesMapper.updateByPrimaryKey(grouponRules);
        return result;
    }

    @Override
    public int deleteGrouponRules(GrouponRules grouponRules) {
        grouponRules.setDeleted(true);
        int result = grouponRulesMapper.updateByPrimaryKey(grouponRules);
        return result;
    }

    @Override
    public GrouponRules getGrouponRulesById(Integer grouponRulesId) {
        GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(grouponRulesId);
        return grouponRules;
    }
}
