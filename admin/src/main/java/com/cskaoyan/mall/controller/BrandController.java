package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.service.BrandService;
import com.cskaoyan.mall.service.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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
    public BaseReqVo listBrand(int page, int limit, Integer id, String name, String sort, String order) {
        //通过brandService获得的map中包含一个brand列表和 查询的总数
        Map<String, Object> data = brandService.getBrandList(page, limit, id, name, sort, order);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(data);
        return baseReqVo;
    }
}
