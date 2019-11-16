package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class CatAndBrandResVo_CatElem {
    private Integer value;
    private String label;
    private List<Category> children;
}
