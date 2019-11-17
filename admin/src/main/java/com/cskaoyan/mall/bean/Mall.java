package com.cskaoyan.mall.bean;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * 该类用于接收前端在系统管理模块传入的商场信息
 */
@Data
public class Mall {
    private String litemall_mall_address;
    private String litemall_mall_name;
    private String litemall_mall_phone;
    private String litemall_mall_qq;
}
