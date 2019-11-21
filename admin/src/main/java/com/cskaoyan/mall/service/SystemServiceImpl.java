package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.System;
import com.cskaoyan.mall.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("systemService")
public class SystemServiceImpl implements SystemService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private SystemPermissionsMapper systemPermissionsMapper;

    @Override
    public Map<String, Object> findAllAdmin(Integer page, Integer limit, String sort, String order, String username) {
        PageHelper.startPage(page, limit, sort + " " + order);
        AdminExample example = new AdminExample();
        if (username != null) {
            example.createCriteria().andUsernameLike("%" +username + "%");
        }
        List<Admin> admins = adminMapper.selectByExample(example);
        PageInfo<Admin> pageInfo = new PageInfo<>(admins);
        long total = pageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", admins);
        map.put("total", total);
        return map;
    }

    @Override
    public List<Map<String, Object>> selectRoles() {
        List<Role> roles = roleMapper.selectByExample(new RoleExample());
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (Role role : roles) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("value", role.getId());
            map.put("label", role.getName());
            list.add(map);
        }
        return list;
    }

    @Override
    public Admin updateAdmin(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
        return adminMapper.selectByPrimaryKey(admin.getId());
    }

    @Override
    public Storage createStorage(Storage storage) {
        storageMapper.insertSelective(storage); // 此处用了 selectKey 插入了 id，故可直接返回
        return storage;
    }

    @Override
    public Map<String, Object> selectLogList(Integer page, Integer limit, String sort, String order, String name) {
        PageHelper.startPage(page, limit, sort + " " + order);
        LogExample example = new LogExample();
        if (name != null) {
            example.createCriteria().andAdminLike("%" + name + "%");
        }
        List<Log> logList = logMapper.selectByExample(example);
        PageInfo<Log> pageInfo = new PageInfo<>(logList);
        long total = pageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", logList);
        map.put("total", total);
        return map;
    }

    @Override
    public Map<String, Object> selectRoleList(Integer page, Integer limit, String sort, String order, String name) {
        PageHelper.startPage(page, limit, sort + " " + order);
        RoleExample example = new RoleExample();
        if (name != null) {
            example.createCriteria().andNameLike("%" + name + "%");
        }
        List<Role> logList = roleMapper.selectByExample(example);
        PageInfo<Role> pageInfo = new PageInfo<>(logList);
        long total = pageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", logList);
        map.put("total", total);
        return map;
    }

    @Override
    public Role createRole(Role role) {
        roleMapper.insertSelective(role);
        return roleMapper.selectByPrimaryKey(role.getId());
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteRole(Role role) {
        roleMapper.deleteByPrimaryKey(role.getId());
    }

    @Override
    public Map<String, Object> selectStorageList(Integer page, Integer limit, String sort, String order, String key, String name) {
        PageHelper.startPage(page, limit, sort + " " + order);
        StorageExample example = new StorageExample();
        StorageExample.Criteria criteria = example.createCriteria();
        if (key != null ) {
            criteria.andKeyLike("%" +key + "%");
        }
        if (name != null) {
            criteria.andNameLike("%" +name + "%");
        }
        List<Storage> storageList = storageMapper.selectByExample(example);
        PageInfo<Storage> pageInfo = new PageInfo<>(storageList);
        long total = pageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", storageList);
        map.put("total", total);
        return map;
    }

    @Override
    public Storage updateStorage(Storage storage) {
        storageMapper.updateByPrimaryKeySelective(storage);
        return storageMapper.selectByPrimaryKey(storage.getId());
    }

    @Override
    public void deleteStorage(Storage storage) {
        storageMapper.deleteByPrimaryKey(storage.getId());
    }

    @Override
    public Admin createAdmin(Admin admin) {
        adminMapper.insertSelective(admin); // 利用 selectKey 返回了 id
        return admin;
    }


    @Override
    public void deleteAdmin(Admin admin) {
        adminMapper.deleteByPrimaryKey(admin.getId());
    }

    @Override
    public Set<String> selectAssignedPermissions(String roleId) {
        if (roleId.equals("1")) {
           return new HashSet<>(permissionMapper.selectAllPermissions());
        }
        return new HashSet<>(permissionMapper.selectPermissionsByRoleId(roleId));
    }
    @Override
    public ArrayList<Map<String, Object>> selectSystemPermissions() {
        SystemPermissionsExample example = new SystemPermissionsExample();
        int i = 0;
        example.createCriteria().andPIdEqualTo(i);
        List<SystemPermissions> systemPermissionsList1 = systemPermissionsMapper.selectByExample(example);
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SystemPermissions systemPermission1 : systemPermissionsList1) {
            i++;
            HashMap<String, Object> stringListHashMap = new HashMap<>();
            stringListHashMap.put("id", systemPermission1.getId());
            stringListHashMap.put("label", systemPermission1.getLabel());

            // 根据 i 依次查询
            SystemPermissionsExample systemPermissionsExample = new SystemPermissionsExample();
            systemPermissionsExample.createCriteria().andPIdEqualTo(i);
            List<SystemPermissions> systemPermissionsList2 = systemPermissionsMapper.selectByExample(systemPermissionsExample);

            ArrayList<Map<String, Object>> permissionsList = new ArrayList<>();

            for (SystemPermissions systemPermissions2 : systemPermissionsList2) {
                // 真正的权限
                HashMap<String, Object> permissionsMap = new HashMap<>();
                permissionsMap.put("id", systemPermissions2.getId());
                permissionsMap.put("label", systemPermissions2.getLabel());

                SystemPermissionsExample permissionsExample = new SystemPermissionsExample();
                permissionsExample.createCriteria().andPIdEqualTo(systemPermissions2.getsId());
                List<SystemPermissions> systemPermissions = systemPermissionsMapper.selectByExample(permissionsExample);
                permissionsMap.put("children", systemPermissions);
                permissionsList.add(permissionsMap);

            }

            stringListHashMap.put("children", permissionsList);

            list.add(stringListHashMap);
        }
        return list;
    }

    @Override
    public void insertPermissionsByRoleId(List<String> permissions, Integer roleId) {
        Permission permission = new Permission();
        permission.setAddTime(new Date());
        permission.setUpdateTime(new Date());
        permission.setRoleId(roleId);
        for (String s : permissions) {
            permission.setPermission(s);
            permissionMapper.insertSelective(permission);
        }
    }

    @Override
    public Double getExpressFreightMin() {
        SystemExample example = new SystemExample();
        example.createCriteria().andKeyNameEqualTo("cskaoyan_mall_express_freight_min");
        List<System> systems = systemMapper.selectByExample(example);
        Double keyValue = Double.valueOf(systems.get(0).getKeyValue());
        return keyValue;
    }

    @Override
    public Double getExpressFreightValue() {
        SystemExample example = new SystemExample();
        example.createCriteria().andKeyNameEqualTo("cskaoyan_mall_express_freight_value");
        List<System> systems = systemMapper.selectByExample(example);
        Double keyValue = Double.valueOf(systems.get(0).getKeyValue());
        return keyValue;
    }

    @Override
    public Integer getIndexNewSize() {
        SystemExample example = new SystemExample();
        example.createCriteria().andKeyNameEqualTo("cskaoyan_mall_wx_index_new");
        List<System> systems = systemMapper.selectByExample(example);
        Integer keyValue = Integer.valueOf(systems.get(0).getKeyValue());
        return keyValue;
    }

    @Override
    public Integer getIndexHotSize() {
        SystemExample example = new SystemExample();
        example.createCriteria().andKeyNameEqualTo("cskaoyan_mall_wx_index_hot");
        List<System> systems = systemMapper.selectByExample(example);
        Integer keyValue = Integer.valueOf(systems.get(0).getKeyValue());
        return keyValue;
    }

    @Override
    public Integer getIndexBrandSize() {
        SystemExample example = new SystemExample();
        example.createCriteria().andKeyNameEqualTo("cskaoyan_mall_wx_index_brand");
        List<System> systems = systemMapper.selectByExample(example);
        Integer keyValue = Integer.valueOf(systems.get(0).getKeyValue());
        return keyValue;
    }

    @Override
    public Integer getIndexTopicSize() {
        SystemExample example = new SystemExample();
        example.createCriteria().andKeyNameEqualTo("cskaoyan_mall_wx_index_topic");
        List<System> systems = systemMapper.selectByExample(example);
        Integer keyValue = Integer.valueOf(systems.get(0).getKeyValue());
        return keyValue;
    }

    @Override
    public Integer getCategoryListSize() {
        SystemExample example = new SystemExample();
        example.createCriteria().andKeyNameEqualTo("cskaoyan_mall_wx_catlog_list");
        List<System> systems = systemMapper.selectByExample(example);
        Integer keyValue = Integer.valueOf(systems.get(0).getKeyValue());
        return keyValue;
    }

    @Override
    public Integer getCategoryGoodsSize() {
        SystemExample example = new SystemExample();
        example.createCriteria().andKeyNameEqualTo("cskaoyan_mall_wx_catlog_goods");
        List<System> systems = systemMapper.selectByExample(example);
        Integer keyValue = Integer.valueOf(systems.get(0).getKeyValue());
        return keyValue;
    }


}
