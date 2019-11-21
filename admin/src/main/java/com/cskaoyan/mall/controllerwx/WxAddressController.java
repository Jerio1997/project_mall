package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.Address;
import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/address")
public class WxAddressController {
    @Autowired
    AddressService addressService;

    @RequestMapping("list")
    public BaseReqVo listAddress(){
        List<Address> data = addressService.listAddress();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
    @RequestMapping("delete")
    public BaseReqVo deleteAddress(@RequestBody Address address){
        Integer id = addressService.deleteAddress(address);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
    @RequestMapping("detail")
    public BaseReqVo detailAddress(Integer id){
        List<Address> status = addressService.detailAddress(id);
        Address data = new Address();
        for (Address address : status) {
            data.setProvinceName(address.getProvince());
            data.setCityName(address.getArea());
            data.setAreaName(address.getArea());
            data.setIsDefault(address.getIsDefault());
            data.setAreaId(address.getAreaId());
            data.setAddress(address.getAddress());
            data.setName(address.getName());
            data.setMobile(address.getMobile());
            data.setId(address.getId());
            data.setCityId(address.getCityId());
            data.setProvinceId(address.getProvinceId());
        }
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }
    @RequestMapping("save")
    public BaseReqVo saveAddress(@RequestBody Address address){
        address.setAddTime(new Date());
        address.setUpdateTime(new Date());
        List<Address> addressList = addressService.saveAddress(address);
        Integer data = 0;
        for (Address address1 : addressList) {
             data = address1.getId();
        }
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(data);
        return baseReqVo;
    }

}
