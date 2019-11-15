package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Brand;
import com.cskaoyan.mall.bean.BrandExample;
import com.cskaoyan.mall.mapper.BrandMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandMapper brandMapper;

    @Override
    public Map<String, Object> getBrandList(int page, int limit, Integer id, String name, String sort, String order) {
        // 注意生成逆向工程文件时没有对 与sql 关键字重名的列名加上"`", 应该手动加上
        PageHelper.startPage(page,limit);
        BrandExample example = new BrandExample();
        BrandExample.Criteria criteria = example.createCriteria();
        if (id != null) {
            criteria.andIdEqualTo(id);
        }
        if (name != null) {
            criteria.andNameLike("%" + name + "%");
        }
        example.setOrderByClause("add_time desc");
        List<Brand> brandList = brandMapper.selectByExample(example);
        PageInfo<Brand> userPageInfo = new PageInfo<>(brandList);
        long total = userPageInfo.getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", brandList);
        return data;
    }

    @Override
    public long getBrandTotal() {
        long countByExample = brandMapper.countByExample(new BrandExample());
        return countByExample;
    }
}
