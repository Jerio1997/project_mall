package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.service.BrandService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("admin/brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    /**
     * 查询符合条件的条目
     * @param id 查询id 精确匹配
     * @param name 查询商品名称 支持模糊查询
     * @param page 页数
     * @param limit 每页条目数
     * @param sort 排序条件
     * @param order 升序或者降序
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions(value={"admin:brand:list"},logical = Logical.OR)
    public BaseReqVo listBrand(int page, int limit, Integer id, String name, String sort, String order) {
        //通过brandService获得的map中包含一个brand列表和 查询的总数
        Map<String, Object> data = brandService.getBrandList(page, limit, id, name, sort, order);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(data);
        return baseReqVo;
    }

    /**
     * 删除一个brand
     * @param brand
     * @return
     */
    @RequestMapping("delete")
    @RequiresPermissions(value={"admin:brand:delete"},logical = Logical.OR)
    public BaseReqVo deleteBrand(@RequestBody Brand brand) {
        int result = brandService.deleteBrand(brand);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @RequestMapping("update")
    @RequiresPermissions(value={"admin:brand:update"},logical = Logical.OR)
    public BaseReqVo updateBrand(@RequestBody Brand brand) {
        brand.setUpdateTime(new Date());
        int result = brandService.updateBrand(brand);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(brand);
        return baseReqVo;
    }

    @RequestMapping("create")
    @RequiresPermissions(value={"admin:brand:create"},logical = Logical.OR)
    public BaseReqVo createBrand(@RequestBody Brand brand) {
        brand.setUpdateTime(new Date());
        brand.setAddTime(new Date());
        int status = brandService.addBrand(brand);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(brand);
        return baseReqVo;
    }

}
