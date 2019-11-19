package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class BrandListReqVo_Wx {
    private Integer totalPages;
    private List<Brand> brandList;
}
