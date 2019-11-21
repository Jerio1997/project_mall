package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Address;
import com.cskaoyan.mall.bean.AddressExample;
import com.cskaoyan.mall.bean.Region;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddressMapper {
    long countByExample(AddressExample example);

    int deleteByExample(AddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Address record);

    int insertSelective(Address record);

    List<Address> selectByExample(AddressExample example);

    Address selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Address record, @Param("example") AddressExample example);

    int updateByExample(@Param("record") Address record, @Param("example") AddressExample example);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);


    String queryProvinceByPid(@Param("provinceId") Integer provinceId);

    String queryCityByPid(@Param("cityId") Integer cityId);

    String queryAreaByPid(@Param("areaId") Integer areaId);

    List<Address> queryAddress(@Param("userId") Integer userId,@Param("name") String name);

    //List<Address> linkAddress();

    List<Address> queryDetailAddress(@Param("id") Integer id);

}
