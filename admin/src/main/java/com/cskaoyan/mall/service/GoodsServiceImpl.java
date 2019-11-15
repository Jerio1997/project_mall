package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.BrandMapper;
import com.cskaoyan.mall.mapper.CategoryMapper;
import com.cskaoyan.mall.mapper.GoodsMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public int queryGoodsCounts(Integer goodsSn, String name) {
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if(!StringUtils.isEmpty(goodsSn)){
            criteria.andGoodsSnEqualTo(goodsSn.toString());
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        long total = goodsMapper.countByExample(goodsExample);
        return (int) total;
    }

    @Override
    public List<Goods> queryGoods(Integer page, Integer limit,Integer goodsSn, String name, String sort, String order) {
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.setOrderByClause(orderByClause);
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if(!StringUtils.isEmpty(goodsSn)){
             criteria.andGoodsSnEqualTo(goodsSn.toString());
        }
        if(!StringUtils.isEmpty(name)){
            criteria.andNameLike("%" + name + "%");
        }
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);

        return goods;
    }

    @Override
    public List<Brand> queryBrands() {
        BrandExample brandExample = new BrandExample();
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        return brands;
    }

    @Override
    public List<CatAndBrandResVo_CatElem > queryNestedCategory() {
        List<CatAndBrandResVo_CatElem > nestedcategoryList = new ArrayList<>();
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelEqualTo("L1");
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        for (Category category : categories) {
            CategoryExample categoryExample2 = new CategoryExample();
            CatAndBrandResVo_CatElem catAndBrandResVo_catElem = new CatAndBrandResVo_CatElem();
            catAndBrandResVo_catElem.setValue(category.getId());
            catAndBrandResVo_catElem.setLabel(category.getName());
            categoryExample2.createCriteria().andLevelEqualTo("L2").andPidEqualTo(category.getId());
            List<Category> children = categoryMapper.selectByExample(categoryExample2);
            catAndBrandResVo_catElem.setChildren(children);
            nestedcategoryList.add(catAndBrandResVo_catElem);
        }
        return nestedcategoryList;
    }
}
