package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Category;

import java.util.List;

public interface CategoryService {


    List<Category> getCategoryList();

    List<Category> getCategoryListForL1();

    int updateCategory(Category category);

    int deleteCategoryByPid(Integer pid);

    int deleteCategory(Category category);

    int addCategory(Category category);
}
