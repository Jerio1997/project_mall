package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.bean.Role;
import com.cskaoyan.mall.bean.Storage;

import java.util.List;
import java.util.Map;

public interface SystemService {
    /**
     * 查询所有的管理员并分页且排序
     * @return 需要返回的 data
     */
    Map<String, Object> findAllAdmin(Integer page, Integer limit, String sort, String order, String username);

    /**
     * 查询所有管理员角色列表
     */
    List<Map<String, Object>> selectRoles();

    /**
     * 修改管理员信息并返回修改后的信息
     */
    Admin updateAdmin(Admin admin);

    /**
     * 创建一个新的内容存储(头像)
     * @param storage
     * @return 返回创建的 Storage
     */
    Storage createStorage(Storage storage);

    /**
     * 返回 key 为 total 和 items 的 map
     */
    Map<String, Object> selectLogList(Integer page, Integer limit, String sort, String order, String name);

    /**
     * 返回 key 为 total 和 items 的 map
     */
    Map<String, Object> selectRoleList(Integer page, Integer limit, String sort, String order, String name);

    /**
     * 返回添加的角色
     */
    Role createRole(Role role);

    /**
     * 编辑角色
     */
    void updateRole(Role role);

    /**
     * 删除角色
     */
    void deleteRole(Role role);

    /**
     * 查询 storage
     */
    Map<String, Object> selectStorageList(Integer page, Integer limit, String sort, String order, String key, String name);

    /**
     * 修改管理员角色
     */
    Storage updateStorage(Storage storage);

    /**
     * 删除管理员角色
     */
    void deleteStorage(Storage storage);

    /**
     * 创建管理员
     */
    Admin createAdmin(Admin admin);

    /**
     * 删除系统管理员
     */
    void deleteAdmin(Admin admin);
}
