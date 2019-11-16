package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.GrouponRules;
import com.cskaoyan.mall.bean.GrouponRulesExample;
import com.cskaoyan.mall.mapper.GrouponRulesMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 17:56
 **/
@Service
public class GrouponRulesServiceImpl implements GrouponRulesService{

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Override
    public int queryGrouponRulesCounts() {
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        long total = grouponRulesMapper.countByExample(grouponRulesExample);
        return (int) total;
    }

    @Override
    public List<GrouponRules> queryGrouponRules(Integer page, Integer limit, Integer goodsId, String sort, String order) {
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        grouponRulesExample.setOrderByClause(orderByClause);
        GrouponRulesExample.Criteria criteria = grouponRulesExample.createCriteria();
        if(!StringUtils.isEmpty(goodsId)){
            criteria.andGoodsIdEqualTo(goodsId);
        }
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(grouponRulesExample);
        return grouponRules;
    }
}
