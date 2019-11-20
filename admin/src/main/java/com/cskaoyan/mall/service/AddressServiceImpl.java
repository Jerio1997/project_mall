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
        AddressExample example = new AddressExample();
        List<Address> data = addressMapper.selectByExample(example);

        return data;
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

}
