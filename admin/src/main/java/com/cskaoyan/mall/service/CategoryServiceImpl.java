package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.CategoryExample;
import com.cskaoyan.mall.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategoryList() {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andPidEqualTo(0); // 一级商品目录查询
        List<Category> categories = categoryMapper.selectByExample(example);
        for (Category category : categories) {
            CategoryExample example1 = new CategoryExample();
            example1.createCriteria().andPidEqualTo(category.getId()); //查询一级商品目录下的二级目录
            List<Category> categories1 = categoryMapper.selectByExample(example1);
            category.setChildren(categories1);
        }
        return categories;
    }

    @Override
    public int updateCategory(Category category) {
        int i = categoryMapper.updateByPrimaryKey(category);
        return i;
    }

    @Override
    public int deleteCategoryByPid(Integer pid) {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andPidEqualTo(pid);
        long l = categoryMapper.countByExample(example);
        if (l != 0) {
            int i = categoryMapper.deleteByExample(example);
            return i;
        }
        return 0;
    }

    @Override
    public int deleteCategory(Category category) {
        int i = categoryMapper.deleteByPrimaryKey(category.getId());
        return i;
    }

    @Override
    public List<Category> getCategoryListForL1() {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andPidEqualTo(0);// 一级商品目录查询
        return categoryMapper.selectByExample(example);
    }
}
