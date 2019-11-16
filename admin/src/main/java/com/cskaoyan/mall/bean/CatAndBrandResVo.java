package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class CatAndBrandResVo {
    private List<CatAndBrandResVo_CatElemChild> brandList;
    private List<CatAndBrandResVo_CatElem > categoryList;
}
