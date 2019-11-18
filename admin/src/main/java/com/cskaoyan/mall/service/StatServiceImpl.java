package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.OrderGoodsMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Author Jerio
 * CreateTime 2019/11/17 23:23
 **/
@Service
public class StatServiceImpl implements StatService{

    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderGoodsMapper orderGoodsMapper;

    @Override
    public StatisticUsers queryStatUser() {
        StatisticUsers statisticUsers = new StatisticUsers();
        //查询所有的user
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        /*criteria.andDeletedEqualTo(false);*/
        //删除的用户还要不要？
        List<StatisticProperty> rows = new ArrayList<>();
        rows = userMapper.queryUserAndDayList();
        statisticUsers.setRows(rows);
        return statisticUsers;
    }

    @Override
    public GoodsStatVo queryStatGoods() {
        GoodsStatVo goodsStatVo = new GoodsStatVo();
        GoodsStatVo.DataBean dataBean = new GoodsStatVo.DataBean();
        List<String> list = new ArrayList<>();
        list.add("day");
        list.add("orders");
        list.add("products");
        list.add("amount");
        dataBean.setColumns(list);

        List<GoodsStatVo.DataBean.RowsBean> rowsBeanList = orderGoodsMapper.selectGoodsStatInfo();
        dataBean.setRows(rowsBeanList);
        goodsStatVo.setData(dataBean);
        return goodsStatVo;
    }






}
