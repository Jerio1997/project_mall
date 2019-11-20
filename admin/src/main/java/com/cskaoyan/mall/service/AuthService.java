package com.cskaoyan.mall.service;


import com.cskaoyan.mall.bean.Admin;

import java.util.ArrayList;
import java.util.List;

public interface AuthService {
    Admin selectAdminByUsername(String username);

    List<String> selectPermissionsByUsername(String username);

    String selectRoleByRoleId(Integer roleId);

    ArrayList<String> selectApiByRoleId(Integer roleId);
}
