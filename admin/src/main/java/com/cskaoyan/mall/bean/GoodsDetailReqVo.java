package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class GoodsDetailReqVo {
    private Integer[] categoryIds;

    private Goods goods;

    private GoodsSpecification[] specifications;

    private GoodsProduct [] products;

    private GoodsAttribute[] attributes;
}
