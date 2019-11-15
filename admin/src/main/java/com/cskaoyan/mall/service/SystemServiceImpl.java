package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.bean.AdminExample;
import com.cskaoyan.mall.bean.Role;
import com.cskaoyan.mall.bean.RoleExample;
import com.cskaoyan.mall.mapper.AdminMapper;
import com.cskaoyan.mall.mapper.RoleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("systemService")
public class SystemServiceImpl implements SystemService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Map<String, Object> findAllAdmin(Integer page, Integer limit, String sort, String order) {
        PageHelper.startPage(page, limit, sort + " " + order);
        AdminExample example = new AdminExample();
        List<Admin> admins = adminMapper.selectByExample(example);
        PageInfo<Admin> pageInfo = new PageInfo<>(admins);
        long total = pageInfo.getTotal();
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", admins);
        map.put("total", total);
        return map;
    }

    @Override
    public List<Role> selectRoles() {
        return roleMapper.selectByExample(new RoleExample());
    }
}
