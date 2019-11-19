package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class FloorGoodsResVo {
    String name;
    List<Goods> goodsList;
    Integer id;
}
