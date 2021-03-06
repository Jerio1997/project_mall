package com.cskaoyan.mall.controller;

import com.aliyun.oss.OSSClient;
import com.cskaoyan.mall.aop.AdminLog;
import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.System;
import com.cskaoyan.mall.component.AliyunComponent;
import com.cskaoyan.mall.service.SystemService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("admin")
public class SystemController {
    @Autowired
    private SystemService systemService;

    @Autowired
    AliyunComponent aliyunComponent;

    @RequestMapping("admin/list")
    @RequiresPermissions(value={"admin:admin:list"},logical = Logical.OR)
    public BaseReqVo<Map<String, Object>> findAllAdmins(Integer page, Integer limit, String sort, String order, String username) {
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = systemService.findAllAdmin(page, limit, sort, order, username);
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

    @AdminLog
    @RequestMapping("admin/update")
    public BaseReqVo<Admin> updateAdmin(@RequestBody Admin admin) {
        BaseReqVo<Admin> baseReqVo = new BaseReqVo<>();
        admin.setUpdateTime(new Date());
        Admin updateAdmin = systemService.updateAdmin(admin);
        baseReqVo.setData(updateAdmin);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @AdminLog
    @RequestMapping("admin/create")
    @RequiresPermissions(value={"admin:admin:create"},logical = Logical.OR)
    public BaseReqVo<Admin> createAdmin(@RequestBody Admin admin) {
        BaseReqVo<Admin> baseReqVo = new BaseReqVo<>();
        admin.setAddTime(new Date());
        admin.setUpdateTime(new Date());
        Admin updateAdmin = systemService.createAdmin(admin);
        baseReqVo.setData(updateAdmin);
        if (updateAdmin != null) {
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
        } else {
            baseReqVo.setErrmsg("创建失败，该管理员已存在！");
            baseReqVo.setErrno(622);
        }
        return baseReqVo;
    }

    @AdminLog
    @RequestMapping("admin/delete")
    @RequiresPermissions(value={"admin:admin:delete"},logical = Logical.OR)
    public Map<String, Object> deleteAdmin(@RequestBody Admin admin) {
        HashMap<String, Object> map = new HashMap<>();
        Admin loginAdmin = (Admin) SecurityUtils.getSubject().getPrincipal();
        if (loginAdmin.getId() == admin.getId()) {
            map.put("errmsg", "失败，你不能自杀");
            map.put("errno", 623);
            return map;
        }
        boolean deleteAdmin = systemService.deleteAdmin(admin);
        if (deleteAdmin) {
            map.put("errmsg", "成功");
            map.put("errno", 0);
        } else {
            map.put("errmsg", "失败，至少存在一名超级管理员");
            map.put("errno", 623);
        }
        return map;
    }

    @RequestMapping("storage/create")
    @RequiresPermissions(value = {"admin:storage:create"},logical = Logical.OR)
    public BaseReqVo<Storage> createStorage(MultipartFile file, HttpServletRequest request) {
        // 不需要 fileupload 组件
        // 拼接 url 前缀与后缀，并且已经在 yml 文件中添加 url 前缀映射路径
//        String urlPrefix = "http://" + request.getServerName() + ":" + request.getServerPort() + "/wx/storage/fetch/";
        //采用oss
        OSSClient ossClient = aliyunComponent.getOssClient();
        String bucket = aliyunComponent.getOss().getBucket();
        String endPoint = aliyunComponent.getOss().getEndPoint();
        String urlPrefix = "https://" + bucket + "." + endPoint + "/";
        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String urlSuffix = split[split.length - 1];
        // 生成随机文件名
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replace("-", "");
        String key = s + "." + urlSuffix;
        // 存储文件
//        File filePath = new File("admin\\target\\classes\\static", key);
        //        修改为文件系统路径
//        File filePath = new File("D:/picture");
//        String absolutePath = filePath.getAbsolutePath();
        try {
            ossClient.putObject(aliyunComponent.getOss().getBucket(), key, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 封装数据
        Storage storage = new Storage();
        storage.setKey(key);
        storage.setName(file.getOriginalFilename());
        storage.setType(file.getContentType());
        storage.setSize(file.getSize());
        storage.setUrl(urlPrefix + key);
        storage.setAddTime(new Date());
        storage.setUpdateTime(new Date());
        storage.setDeleted(false);
        BaseReqVo<Storage> baseReqVo = new BaseReqVo<>();
        Storage createdStorage = systemService.createStorage(storage);
        baseReqVo.setData(createdStorage);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("log/list")
    @RequiresPermissions(value = {"admin:log:list"},logical = Logical.OR)
    public BaseReqVo<Map<String, Object>> logList(Integer page, Integer limit, String sort, String order, String name) {
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = systemService.selectLogList(page, limit, sort, order, name);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("role/list")
    @RequiresPermissions(value = {"admin:role:list"},logical = Logical.OR)
    public BaseReqVo<Map<String, Object>> roleList(Integer page, Integer limit, String sort, String order, String name){
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>();
        Map<String, Object> map = systemService.selectRoleList(page, limit, sort, order, name);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @AdminLog
    @RequestMapping("role/create")
    @RequiresPermissions(value = {"admin:role:create"},logical = Logical.OR)
    public BaseReqVo<Role> createRole(@RequestBody Role role) {
        BaseReqVo<Role> baseReqVo = new BaseReqVo<>();
        role.setAddTime(new Date());
        role.setUpdateTime(new Date());
        Role createdRole = systemService.createRole(role);
        if (createdRole != null) {
            baseReqVo.setData(createdRole);
            baseReqVo.setErrmsg("成功");
            baseReqVo.setErrno(0);
        } else {
            baseReqVo.setErrmsg("失败，该角色已存在");
            baseReqVo.setErrno(624);
        }
        return baseReqVo;
    }

    @AdminLog
    @RequestMapping("role/update")
    @RequiresPermissions(value = {"admin:role:update"},logical = Logical.OR)
    public Map<String, Object> updateRole(@RequestBody Role role) {
        HashMap<String, Object> map = new HashMap<>();
        role.setUpdateTime(new Date());
        systemService.updateRole(role);
        map.put("errmsg", "成功");
        map.put("errno", 0);
        return map;
    }

    @AdminLog
    @RequestMapping("role/delete")
    @RequiresPermissions(value = {"admin:role:delete"},logical = Logical.OR)
    public Map<String, Object> deleteRole(@RequestBody Role role) {
        HashMap<String, Object> map = new HashMap<>();
        boolean deleteRole = systemService.deleteRole(role);
        if (deleteRole) {
            map.put("errmsg", "成功");
            map.put("errno", 0);
        } else {
            map.put("errmsg", "当前角色存在管理员，不能删除");
            map.put("errno", 642);
        }
        return map;
    }

    @RequestMapping(value = "role/permissions", method = RequestMethod.GET)
    @RequiresPermissions(value = {"admin:role:permission:get"},logical = Logical.OR)
    public BaseReqVo<Map<String, Object>> rolePermissions(String roleId) {
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>();

        Set<String> assignedPermissions = systemService.selectAssignedPermissions(roleId);
        ArrayList<Map<String, Object>> systemPermissions = systemService.selectSystemPermissions();

        HashMap<String, Object> map = new HashMap<>();
        map.put("assignedPermissions", assignedPermissions);
        map.put("systemPermissions", systemPermissions);
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @PostMapping("role/permissions")
    @RequiresPermissions(value = {"admin:log:list"},logical = Logical.OR)
    public BaseReqVo allocatePermissions(@RequestBody Map<String, Object> data) {
        List<String> permissions = (List<String>) data.get("permissions");
        Integer roleId = (Integer) data.get("roleId");
        systemService.insertPermissionsByRoleId(permissions, roleId);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }


    @RequestMapping("storage/list")
    @RequiresPermissions(value = {"admin:storage:list"},logical = Logical.OR)
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
    @RequiresPermissions(value = {"admin:storage:update"},logical = Logical.OR)
    public BaseReqVo<Storage> updateStorage(@RequestBody Storage storage) {
        BaseReqVo<Storage> baseReqVo = new BaseReqVo<>();
        Storage updatedStorage = systemService.updateStorage(storage);
        baseReqVo.setData(updatedStorage);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("storage/delete")
    @RequiresPermissions(value = {"admin:storage:delete"},logical = Logical.OR)
    public Map<String, Object> deleteStorage(@RequestBody Storage storage) {
        HashMap<String, Object> map = new HashMap<>();
        systemService.deleteStorage(storage);
        map.put("errmsg", "成功");
        map.put("errno", 0);
        return map;
    }
}
