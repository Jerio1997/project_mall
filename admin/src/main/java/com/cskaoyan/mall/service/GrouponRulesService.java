package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Groupon;
import com.cskaoyan.mall.bean.GrouponRules;
import com.cskaoyan.mall.bean.GrouponRulesListResVo;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 17:55
 **/
public interface GrouponRulesService {

    int queryGrouponRulesCounts();

    GrouponRulesListResVo queryGrouponRules(Integer page, Integer limit, Integer goodsId, String sort, String order);

    int createGrouponRules(GrouponRules grouponRules);

    int updateGrouponRules(GrouponRules grouponRules);

    int deleteGrouponRules(GrouponRules grouponRules);

}
