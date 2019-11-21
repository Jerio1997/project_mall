package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Address;

import java.util.List;

public interface AddressService {
    List<Address> listAddress();

    Integer deleteAddress(Address address);

    List<Address> saveAddress(Address address);

    List<Address> detailAddress(Integer id);
}
