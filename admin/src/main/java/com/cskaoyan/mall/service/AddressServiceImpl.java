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
    public List<Address> listAddress() {
        AddressExample addressExample = new AddressExample();
        List<Address> addresses = addressMapper.selectByExample(addressExample);

        for (Address address : addresses) {
            String province = addressMapper.queryProvinceByPid(address.getProvinceId());
            String city = addressMapper.queryProvinceByPid(address.getCityId());
            String area = addressMapper.queryProvinceByPid(address.getAreaId());
            address.setProvince(province);
            address.setCity(city);
            address.setArea(area);
            String s = address.getAddress();
            address.setDetailedAddress(province+city+area+s);
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
    public List<Address> saveAddress(Address address) {
        AddressExample example = new AddressExample();
        List<Address> addressList = addressMapper.selectByExample(example);
        addressMapper.insertSelective(address);
        return addressList;
    }

    @Override
    public List<Address> detailAddress(Integer id) {
        List<Address> addresses = addressMapper.queryDetailAddress(id);
        for (Address address : addresses) {
            String province = addressMapper.queryProvinceByPid(address.getProvinceId());
            String city = addressMapper.queryProvinceByPid(address.getCityId());
            String area = addressMapper.queryProvinceByPid(address.getAreaId());
            address.setProvince(province);
            address.setCity(city);
            address.setArea(area);
        }
        return addresses;
    }

    @Override
    public Address getAddressById(int addressId) {
        Address address = addressMapper.selectByPrimaryKey(addressId);
        return address;
    }
}
