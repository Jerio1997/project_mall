package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Keyword;
import com.cskaoyan.mall.service.KeywordService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("admin/keyword")
public class KeywordController {

    @Autowired
    KeywordService keywordService;

    @RequestMapping("list")
    @RequiresPermissions(value={"admin:keyword:list"},logical = Logical.OR)
    public BaseReqVo listKeyword(int page, int limit, String keyword, String url, String sort, String order) {
        Map<String, Object> data = keywordService.getKeyword(page, limit, keyword, url, sort, order);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(data);
        return baseReqVo;
    }

    @RequestMapping("create")
    @RequiresPermissions(value={"admin:keyword:create"},logical = Logical.OR)
    public BaseReqVo createKeyword(@RequestBody Keyword keyword) {
        keyword.setAddTime(new Date());
        keyword.setUpdateTime(new Date());
        int status = keywordService.addKeyword(keyword);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(keyword);
        return baseReqVo;
    }

    @RequestMapping("update")
    @RequiresPermissions(value={"admin:keyword:update"},logical = Logical.OR)
    public BaseReqVo updateKeyword(@RequestBody Keyword keyword) {
        keyword.setUpdateTime(new Date());
        int status = keywordService.updateKeyword(keyword);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(keyword);
        return baseReqVo;
    }

    @RequestMapping("delete")
    @RequiresPermissions(value={"admin:keyword:delete"},logical = Logical.OR)
    public BaseReqVo deleteKeyword(@RequestBody Keyword keyword) {
        int status = keywordService.deleteKeyword(keyword);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

}
