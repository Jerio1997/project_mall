package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Address;

import java.util.List;

public interface AddressService {
    List<Address> listAddress(Integer userId, String name);

    Integer deleteAddress(Address address);

    Integer saveAddress(Address address);

    Address getAddressById(int addressId);
}
