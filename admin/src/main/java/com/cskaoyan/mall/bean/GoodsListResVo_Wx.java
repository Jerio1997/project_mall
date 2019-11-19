package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;
@Data
public class GoodsListResVo_Wx {
    //goods number
    private Integer count;
    private List<Goods> goodsList;
    private List<Category> filterCategoryList;
}
