package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.GrouponRulesService;
import com.cskaoyan.mall.service.GrouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    GrouponService grouponService;

    @GetMapping("list")
    public BaseReqVo<GrouponRulesListResVo> listGrouponRules(Integer page, Integer limit, Integer goodsId, String sort, String order){
        GrouponRulesListResVo grouponRulesListResVo;
        grouponRulesListResVo = grouponRulesService.queryGrouponRules(page,limit,goodsId,sort,order);
        BaseReqVo<GrouponRulesListResVo> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(grouponRulesListResVo);
        return baseReqVo;
    }

    @PostMapping("create")
    public BaseReqVo createGrouponRules(@RequestBody GrouponRules grouponRules){
        int result = grouponRulesService.createGrouponRules(grouponRules);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(grouponRules);
        return baseReqVo;
    }

    @PostMapping("update")
    public BaseReqVo updateGrouponRules(@RequestBody GrouponRules grouponRules){
        int result = grouponRulesService.updateGrouponRules(grouponRules);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(grouponRules);
        return baseReqVo;
    }

    @PostMapping("delete")
    public BaseReqVo deleteGrouponRules(@RequestBody GrouponRules grouponRules){
        int result = grouponRulesService.deleteGrouponRules(grouponRules);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    @GetMapping("listRecord")
    public BaseReqVo<GrouponRecordListResVo> listRecord(Integer page, Integer limit, Integer goodsId, String sort, String order){
        GrouponRecordListResVo grouponRecordListResVo;
        grouponRecordListResVo = grouponService.getGrouponRecord(page,limit,goodsId,sort,order);
        BaseReqVo<GrouponRecordListResVo> baseReqVo = new BaseReqVo<>();
        baseReqVo.setData(grouponRecordListResVo);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;

    }



}
