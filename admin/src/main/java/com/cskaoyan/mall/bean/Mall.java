package com.cskaoyan.mall.bean;

import lombok.Data;

/**
 * 该类用于接收前端在系统管理模块传入的商场信息
 */
@Data
public class Mall {
    private String litemallMallAddress;
    private String litemallMallName;
    private String litemallMallPhone;
    private String litemallMallQQ;
}
