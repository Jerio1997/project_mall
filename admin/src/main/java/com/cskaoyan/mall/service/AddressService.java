package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Address;

import java.util.List;

public interface AddressService {
    List<Address> listAddress();

    Integer deleteAddress(Address address);

//    Integer saveAddress(Address address);

    Address getAddressById(int addressId);

    List<Address> saveAddress(Address address);

    List<Address> detailAddress(Integer id);
}
