package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("list")
    public BaseReqVo listCategory() {
        List<Category> categoryList = categoryService.getCategoryList();
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(categoryList);
        return baseReqVo;
    }

    @RequestMapping("l1")
    public BaseReqVo listForLevel1() {
        List<Category> categoryList = categoryService.getCategoryListForL1();
        List<Map> listForLevel1 = new ArrayList<>();
        for (Category category : categoryList) {
            Map<String, Object> labelMap = new HashMap<>();
            labelMap.put("value", category.getId());
            labelMap.put("label", category.getName());
            listForLevel1.add(labelMap);
        }
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(listForLevel1);
        return baseReqVo;
    }

    @RequestMapping("update")
    public BaseReqVo updateCategory(@RequestBody Category category) {
        categoryService.updateCategory(category);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("delete")
    public BaseReqVo deleteCategory(@RequestBody Category category) {
        categoryService.deleteCategoryByPid(category.getId());
        categoryService.deleteCategory(category);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
