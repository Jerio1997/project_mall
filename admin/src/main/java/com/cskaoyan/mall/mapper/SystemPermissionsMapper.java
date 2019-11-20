package com.cskaoyan.mall.mapper;

import java.util.ArrayList;

public interface SystemPermissionsMapper {

    ArrayList<String> selectApiByRoleId(Integer roleId);
}
