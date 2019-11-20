package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Category;
import com.cskaoyan.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/catalog")
public class WxCatalogController {

    private static final int CURRENT_CATALOG_ID = 0;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("index")
    public BaseReqVo<Map<String, Object>> catalogIndex() {
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>();
        List<Category> categoryList = categoryService.selectCategoryList();
        Category currentCategory = categoryList.get(0);
        List<Category> currentSubCategory = categoryService.selectCurrentSubCategoryByPid(currentCategory.getPid());
        HashMap<String, Object> data = new HashMap<>();
        data.put("categoryList", categoryList);
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        baseReqVo.setData(data);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("current")
    public BaseReqVo<Map<String, Object>> currentCatalog(@RequestParam("id") Integer id) {
        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>();
        Category currentCategory = categoryService.selectCurrentCategoryById(id);
        List<Category> currentSubCategory = categoryService.selectCurrentSubCategoryByPid(currentCategory.getPid());
        HashMap<String, Object> data = new HashMap<>();
        data.put("currentCategory", currentCategory);
        data.put("currentSubCategory", currentSubCategory);
        baseReqVo.setData(data);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
