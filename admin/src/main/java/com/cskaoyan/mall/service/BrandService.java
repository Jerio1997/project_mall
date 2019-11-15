package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Brand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    Map<String, Object> getBrandList(int page, int limit, Integer id, String name, String sort, String order);

    long getBrandTotal();
}