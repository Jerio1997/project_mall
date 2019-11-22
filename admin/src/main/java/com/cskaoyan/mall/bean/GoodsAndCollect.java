package com.cskaoyan.mall.bean;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GoodsAndCollect {
    private String brief;
    private Integer id;
    private String name;
    private String picUrl;
    private BigDecimal retailPrice;
    private Byte type;
    private Integer valueId;
}
