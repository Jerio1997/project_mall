package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/18 21:27
 **/
@Data
public class GrouponRecord {

    Groupon groupon;

    Goods goods;

    GrouponRules rules;

    List<Groupon> subGroupons;

}
