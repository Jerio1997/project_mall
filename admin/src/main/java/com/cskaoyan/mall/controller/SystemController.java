package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.System;
import java.net.URL;
import java.util.*;

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
    public BaseReqVo<List<Map<String, Object>>> selectRoles() {
        BaseReqVo<List<Map<String, Object>>> baseReqVo = new BaseReqVo<>();
        List<Map<String, Object>> list = systemService.selectRoles();
        baseReqVo.setData(list);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("admin/update")
    public BaseReqVo<Admin> updateAdmin(@RequestBody Admin admin) {
        BaseReqVo<Admin> baseReqVo = new BaseReqVo<>();
        Admin updateAdmin = systemService.updateAdmin(admin);
        baseReqVo.setData(updateAdmin);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    /*@RequestMapping("storage/create")
    public BaseReqVo<Storage> createStorage(MultipartFile file, HttpServletRequest request) {
        // 生成随机文件名
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replace("-", "");
        String key = s + file.getOriginalFilename();
        // 存储文件
        File filePath = new File("admin\\target\\classes\\static", key);
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 封装数据
        Storage storage = new Storage();
        storage.setKey(key);
        storage.setName(file.getOriginalFilename());
        storage.setType(file.getContentType());
        storage.setSize(file.getSize());
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        System.out.println(filePath);
        storage.setUrl(serverName + ":" +serverPort + "\\" + key);
        storage.setAddTime(new Date());
        storage.setUpdateTime(new Date());
        storage.setDeleted(false);
        BaseReqVo<Storage> baseReqVo = new BaseReqVo<>();
        Storage createdStorage = systemService.createStorage(storage);
        baseReqVo.setData(createdStorage);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }*/

    @RequestMapping("log/list")
    public BaseReqVo<Map<String, Object>> logList(Integer page, Integer limit, String sort, String order, String name) {
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = systemService.selectLogList(page, limit, sort, order, name);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("role/list")
    public BaseReqVo<Map<String, Object>> roleList(Integer page, Integer limit, String sort, String order, String name){
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = systemService.selectRoleList(page, limit, sort, order, name);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("role/create")
    public BaseReqVo<Role> createRole(@RequestBody Role role) {
        BaseReqVo<Role> baseReqVo = new BaseReqVo<>();
        role.setAddTime(new Date());
        role.setUpdateTime(new Date());
        Role createdRole = systemService.createRole(role);
        baseReqVo.setData(createdRole);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("role/update")
    public Map<String, Object> updateRole(@RequestBody Role role) {
        HashMap<String, Object> map = new HashMap<>();
        systemService.updateRole(role);
        map.put("errmsg", "成功");
        map.put("errno", 0);
        return map;
    }

    @RequestMapping("role/delete")
    public Map<String, Object> deleteRole(@RequestBody Role role) {
        HashMap<String, Object> map = new HashMap<>();
        systemService.deleteRole(role);
        map.put("errmsg", "成功");
        map.put("errno", 0);
        return map;
    }

    @RequestMapping("storage/list")
    public BaseReqVo<Map<String, Object>> storageList(Integer page, Integer limit, String sort,
                                                      String order, String key,String name) {
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = systemService.selectStorageList(page, limit, sort, order, key, name);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("storage/update")
    public BaseReqVo<Storage> updateStorage(@RequestBody Storage storage) {
        BaseReqVo<Storage> baseReqVo = new BaseReqVo<>();
        Storage updatedStorage = systemService.updateStorage(storage);
        baseReqVo.setData(updatedStorage);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("storage/delete")
    public Map<String, Object> deleteStorage(@RequestBody Storage storage) {
        HashMap<String, Object> map = new HashMap<>();
        systemService.deleteStorage(storage);
        map.put("errmsg", "成功");
        map.put("errno", 0);
        return map;
    }
}
