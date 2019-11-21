package com.cskaoyan.mall.mapper;

import org.apache.ibatis.annotations.Param;
import com.cskaoyan.mall.bean.SystemPermissions;
import com.cskaoyan.mall.bean.SystemPermissionsExample;

import java.util.ArrayList;
import java.util.List;

public interface SystemPermissionsMapper {

    ArrayList<String> selectApiByRoleId(Integer roleId);

    long countByExample(SystemPermissionsExample example);

    int deleteByExample(SystemPermissionsExample example);

    int deleteByPrimaryKey(Integer sId);

    int insert(SystemPermissions record);

    int insertSelective(SystemPermissions record);

    List<SystemPermissions> selectByExample(SystemPermissionsExample example);

    SystemPermissions selectByPrimaryKey(Integer sId);

    int updateByExampleSelective(@Param("record") SystemPermissions record, @Param("example") SystemPermissionsExample example);

    int updateByExample(@Param("record") SystemPermissions record, @Param("example") SystemPermissionsExample example);

    int updateByPrimaryKeySelective(SystemPermissions record);

    int updateByPrimaryKey(SystemPermissions record);
}
