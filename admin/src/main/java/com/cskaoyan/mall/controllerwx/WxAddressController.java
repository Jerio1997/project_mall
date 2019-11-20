package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.Address;
import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @RequestMapping("save")
    public BaseReqVo saveAddress(@RequestBody Address address){
        Integer i = addressService.saveAddress(address);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

}
