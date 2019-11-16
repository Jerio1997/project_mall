package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 18:12
 **/
@Data
public class GrouponRulesListResVo {

    private Integer total;
    private List<GrouponRules> items;
}
