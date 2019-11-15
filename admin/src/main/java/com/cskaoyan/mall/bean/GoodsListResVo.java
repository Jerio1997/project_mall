package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class GoodsListResVo {
    private Integer total;
    private List<Goods> items;
}
