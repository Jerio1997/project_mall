package com.cskaoyan.mall.controller;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.AuthService;
import com.cskaoyan.mall.shiro.CustomToken;
import com.cskaoyan.mall.utils.Md5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @RequestMapping("login")
    public BaseRespVo login(@RequestBody LoginVo loginVo){

        /*BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setData("4b7d719e-53b7-4019-9677-6309b2445b45");
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;*/

        Subject subject = SecurityUtils.getSubject();
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
        try {
            String md5Password = Md5Util.getMd5(password);
            CustomToken customToken = new CustomToken(username, md5Password, "admin");
            subject.login(customToken);
        } catch (AuthenticationException | NoSuchAlgorithmException e) {
            return BaseRespVo.fail(515,"登陆失败");
        }
        Serializable sessionId = subject.getSession().getId();
        return BaseRespVo.ok(sessionId);
    }

    @RequestMapping("info")
    public BaseReqVo info(String token){
        BaseReqVo baseReqVo = new BaseReqVo();
        InfoData data = new InfoData();
        Subject subject = SecurityUtils.getSubject();
        Admin admin = (Admin) subject.getPrincipal();
        String username = admin.getUsername();
        data.setName(username);
        data.setAvatar(admin.getAvatar());
        Integer[] roleIds = admin.getRoleIds();
        String role = null;
        ArrayList<String> roles = new ArrayList<>();
        ArrayList<String> apis = new ArrayList<>();
        ArrayList<String> perms = new ArrayList<>();
        for (Integer roleId : roleIds) {
            role = authService.selectRoleByRoleId(roleId);
            perms = authService.selectApiByRoleId(roleId);
            roles.add(role);
            apis.addAll(perms);
        }
        if (apis.size() == 0){
            apis.add("*");
        }
        data.setPerms(apis);
        data.setRoles(roles);
        baseReqVo.setData(data);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);


        return baseReqVo;
    }

    @RequestMapping("logout")
    public BaseReqVo logout(){
        SecurityUtils.getSubject().logout();
        BaseReqVo<Object> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("unauthenticated")
    public BaseRespVo unauthenticated(){

        return BaseRespVo.fail(516,"请登录再试");
    }
}
