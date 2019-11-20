package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Issue;
import com.cskaoyan.mall.service.IssueService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("admin/issue")
public class IssueController {

    @Autowired
    IssueService issueService;

    @RequestMapping("list")
    @RequiresPermissions(value={"admin:issue:list"},logical = Logical.OR)
    public BaseReqVo listIssue(int page, int limit, String question, String sort, String order) {
        Map<String, Object> data = issueService.getIssueList(page, limit, question, sort, order);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }

    @RequestMapping("create")
    @RequiresPermissions(value={"admin:issue:create"},logical = Logical.OR)
    public BaseReqVo createIssue(@RequestBody Issue issue) {
        issue.setAddTime(new Date());
        issue.setUpdateTime(new Date());
        int status = issueService.addIssue(issue);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(issue);
        return baseReqVo;
    }

    @RequestMapping("delete")
    @RequiresPermissions(value={"admin:issue:delete"},logical = Logical.OR)
    public BaseReqVo deleteIssue(@RequestBody Issue issue) {
        int status = issueService.deleteIssue(issue);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("update")
    @RequiresPermissions(value={"admin:issue:update"},logical = Logical.OR)
    public BaseReqVo updateIssue(@RequestBody Issue issue) {
        issue.setUpdateTime(new Date());
        int status = issueService.updateIssue(issue);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

}
