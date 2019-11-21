package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Address;
import com.cskaoyan.mall.bean.AddressExample;
import com.cskaoyan.mall.mapper.AddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    AddressMapper addressMapper;
    @Override
    public List<Address> listAddress(Integer userId, String name) {
        List<Address> addresses = addressMapper.queryAddress(userId,name);
        for (Address address : addresses) {
            String province = addressMapper.queryProvinceByPid(address.getProvinceId());
            String city = addressMapper.queryProvinceByPid(address.getCityId());
            String area = addressMapper.queryProvinceByPid(address.getAreaId());
            address.setProvince(province);
            address.setCity(city);
            address.setArea(area);
            address.setDetailedAddress(province+city+area);
        }
        return addresses;
    }

    @Override
    public Integer deleteAddress(Address address) {
        address.setDeleted(true);
        //address.setUpdateTime(new Date());
        Integer i = addressMapper.deleteByPrimaryKey(address.getId());
        return i;
    }

    @Override
    public Integer saveAddress(Address address) {
        Integer i = addressMapper.updateByPrimaryKey(address);
        return i;
    }

    @Override
    public Address getAddressById(int addressId) {
        Address address = addressMapper.selectByPrimaryKey(addressId);
        return address;
    }
}
