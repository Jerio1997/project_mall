package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.CategoryResVo_Wx;

import java.util.List;

public interface CategoryService {


    List<Category> getCategoryList();

    List<Category> getCategoryListForL1();

    int updateCategory(Category category);

    int deleteCategoryByPid(Integer pid);

    int deleteCategory(Category category);

    int addCategory(Category category);

    List<Category> getCategoryList(int page, int limit);

    Category getCategoryById(Integer id);

    CategoryResVo_Wx queryNestedCategory(Integer id);

    List<Category> selectCategoryList();

    List<Category> selectCurrentSubCategoryByPid(Integer pid);

    Category selectCurrentCategoryById(Integer id);
}
