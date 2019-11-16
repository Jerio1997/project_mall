package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.GrouponListResVo;
import com.cskaoyan.mall.bean.GrouponRules;
import com.cskaoyan.mall.bean.GrouponRulesListResVo;
import com.cskaoyan.mall.service.GrouponRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 16:20
 **/

@RestController
@RequestMapping("admin/groupon")
public class GrouponController {

    @Autowired
    GrouponRulesService grouponRulesService;

    @GetMapping("list")
    public BaseReqVo<GrouponRulesListResVo> listGrouponRules(Integer page, Integer limit, Integer goodsId, String sort, String order){
        BaseReqVo<GrouponRulesListResVo> grouponRulesListResVoBaseReqVo = new BaseReqVo<>();
        int total = grouponRulesService.queryGrouponRulesCounts();
        List<GrouponRules> groupons = grouponRulesService.queryGrouponRules(page,limit,goodsId,sort,order);
        GrouponRulesListResVo grouponRulesListResVo = new GrouponRulesListResVo();
        grouponRulesListResVo.setItems(groupons);
        grouponRulesListResVo.setTotal(total);
        grouponRulesListResVoBaseReqVo.setErrno(0);
        grouponRulesListResVoBaseReqVo.setErrmsg("成功");
        grouponRulesListResVoBaseReqVo.setData(grouponRulesListResVo);
        return grouponRulesListResVoBaseReqVo;
    }



}
