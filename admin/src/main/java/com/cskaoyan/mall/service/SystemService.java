package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Role;

import java.util.List;
import java.util.Map;

public interface SystemService {
    Map<String, Object> findAllAdmin(Integer page, Integer limit, String sort, String order);
    List<Role> selectRoles();
}
