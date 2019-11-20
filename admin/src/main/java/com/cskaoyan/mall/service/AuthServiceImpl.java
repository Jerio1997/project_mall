package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.bean.AdminExample;
import com.cskaoyan.mall.mapper.AdminMapper;
import com.cskaoyan.mall.mapper.PermissionMapper;
import com.cskaoyan.mall.mapper.RoleMapper;
import com.cskaoyan.mall.mapper.SystemPermissionsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    SystemPermissionsMapper systemPermissionsMapper;

    @Override
    public Admin selectAdminByUsername(String username) {
        Admin admin = adminMapper.selectAdminByUsername(username);
        return admin;
    }

    @Override
    public List<String> selectPermissionsByUsername(String username) {
        Admin admin = adminMapper.selectAdminByUsername(username);
        Integer[] roleIds = admin.getRoleIds();
        List<String> permissions = new ArrayList<>();
        for (Integer roleId : roleIds) {
            List<String> permissionlist = permissionMapper.selectPermissionByRoleId(roleId);
            permissions.addAll(permissionlist);
        }
        return permissions;
    }

    @Override
    public String selectRoleByRoleId(Integer roleId) {

        return roleMapper.selectRoleById(roleId);
    }

    @Override
    public ArrayList<String> selectApiByRoleId(Integer roleId) {
        return systemPermissionsMapper.selectApiByRoleId(roleId);
    }
}
