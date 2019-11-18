package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.StatisticProperty;
import com.cskaoyan.mall.bean.StatisticUsers;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.bean.UserExample;
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


}
