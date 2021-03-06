package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.bean.CategoryExample;
import com.cskaoyan.mall.bean.CategoryResVo_Wx;
import com.cskaoyan.mall.mapper.CategoryMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Category> getCategoryList() {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andPidEqualTo(0).andDeletedEqualTo(false); // 一级商品目录查询
        List<Category> categories = categoryMapper.selectByExample(example);
        for (Category category : categories) {
            CategoryExample example1 = new CategoryExample();
            example1.createCriteria().andPidEqualTo(category.getId()).andDeletedEqualTo(false); //查询一级商品目录下的二级目录
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
        // 直接删除
//        int i = categoryMapper.deleteByPrimaryKey(category.getId());
        // 修改 删除的标志位
        category.setDeleted(true);
        category.setUpdateTime(new Date());
        int i = categoryMapper.updateByPrimaryKey(category);
        return i;
    }

    @Override
    public int addCategory(Category category) {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andPidEqualTo(category.getPid());
        example.setOrderByClause("sort_order desc");
        List<Category> categoryList = categoryMapper.selectByExample(example);
        Byte sortOrder;
        if (categoryList == null || categoryList.size() == 0) {
            sortOrder = 0;
        } else {
            sortOrder = categoryList.get(0).getSortOrder();
        }
        category.setSortOrder((byte) (sortOrder+1));
        int i = categoryMapper.insertSelectiveAndGetId(category);
        return i;
    }

    @Override
    public List<Category> getCategoryListForL1() {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andPidEqualTo(0);// 一级商品目录查询
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<Category> getCategoryList(int page, int limit) {
        PageHelper.startPage(page, limit);
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelEqualTo("L1");
        List<Category> categories = categoryMapper.selectByExample(example);
        return categories;
    }

    @Override
    public Category getCategoryById(Integer id) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andIdEqualTo(id);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        Category category = categories.get(0);
        return category;
    }

    @Override
    public CategoryResVo_Wx queryNestedCategory(Integer id) {
        Category category = getCategoryById(id);
        CategoryResVo_Wx categoryResVo_wx = new CategoryResVo_Wx();
        if ("L1".equals(category.getLevel())){
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.createCriteria().andPidEqualTo(id);
            List<Category> brotherCategory = categoryMapper.selectByExample(categoryExample);
            Category category1 = brotherCategory.get(0);
            categoryResVo_wx.setCurrentCategory(category1);
            categoryResVo_wx.setParentCategory(category);
            categoryResVo_wx.setBrotherCategory(brotherCategory);

        }else if("L2".equals(category.getLevel())){
            CategoryExample categoryExample = new CategoryExample();
            categoryExample.createCriteria().andPidEqualTo(category.getPid());
            List<Category> brotherCategory = categoryMapper.selectByExample(categoryExample);
           categoryResVo_wx.setBrotherCategory(brotherCategory);

            CategoryExample categoryExample1 = new CategoryExample();
            categoryExample1.createCriteria().andIdEqualTo(category.getPid());
            List<Category> parentCategories = categoryMapper.selectByExample(categoryExample1);
            Category parentCategory = parentCategories.get(0);
            categoryResVo_wx.setParentCategory(parentCategory);
            categoryResVo_wx.setCurrentCategory(category);
        }
        return categoryResVo_wx;

    }

    @Override
    public List<Category> selectCategoryList() {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andPidEqualTo(0).andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public List<Category> selectCurrentSubCategoryByPid(Integer pid) {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andPidEqualTo(pid).andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public Category selectCurrentCategoryById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }
}
