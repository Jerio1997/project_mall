package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.GoodsStatVo;
import com.cskaoyan.mall.bean.StatisticUsers;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/17 23:23
 **/
public interface StatService {
    StatisticUsers queryStatUser();

    GoodsStatVo queryStatGoods();

}
