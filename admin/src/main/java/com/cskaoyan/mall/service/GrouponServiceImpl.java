package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Groupon;
import com.cskaoyan.mall.bean.GrouponExample;
import com.cskaoyan.mall.mapper.GrouponMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 16:21
 **/

@Service
public class GrouponServiceImpl implements GrouponService{

    @Autowired
    GrouponMapper grouponMapper;

    @Override
    public int queryGrouponCounts() {
        GrouponExample grouponExample = new GrouponExample();
        long total = grouponMapper.countByExample(grouponExample);
        return (int) total;
    }

    @Override
    public List<Groupon> queryGroupon(Integer page, Integer limit, Integer goodsId, String sort, String order) {
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        GrouponExample grouponExample = new GrouponExample();
        grouponExample.setOrderByClause(orderByClause);
        GrouponExample.Criteria criteria = grouponExample.createCriteria();
        if(!StringUtils.isEmpty(goodsId)){
            criteria.andGrouponIdEqualTo(goodsId);
        }
        List<Groupon> groupons = grouponMapper.selectByExample(grouponExample);
        return groupons;
    }
}
