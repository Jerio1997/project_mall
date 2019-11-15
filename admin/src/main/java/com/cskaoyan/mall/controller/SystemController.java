package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Role;
import com.cskaoyan.mall.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
public class SystemController {
    @Autowired
    private SystemService systemService;

    @RequestMapping("admin/list")
    public BaseReqVo<Map<String, Object>> findAllAdmins(Integer page, Integer limit, String sort, String order) {
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = systemService.findAllAdmin(page, limit, sort, order);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("role/options")
    public BaseReqVo<List<Role>> selectRoles() {
        BaseReqVo<List<Role>> baseReqVo = new BaseReqVo<>();
        List<Role> roles = systemService.selectRoles();
        baseReqVo.setData(roles);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
