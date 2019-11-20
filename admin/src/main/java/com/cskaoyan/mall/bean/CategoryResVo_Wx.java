package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;


@Data
public class CategoryResVo_Wx {

    private Category currentCategory;

    private Category parentCategory;

    private List<Category> brotherCategory;

}
