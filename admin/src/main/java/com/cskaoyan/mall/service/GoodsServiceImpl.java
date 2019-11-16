package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    GoodsAttributeMapper goodsAttributeMapper;
    @Autowired
    GoodsSpecificationMapper goodsSpecificationMapper;
    @Autowired
    GoodsProductMapper goodsProductMapper;

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
    public List<CatAndBrandResVo_CatElemChild> queryBrands() {
        BrandExample brandExample = new BrandExample();
        List<Brand> brandsTemp = brandMapper.selectByExample(brandExample);
        ArrayList<CatAndBrandResVo_CatElemChild> brands = new ArrayList<>();
        for (Brand brand : brandsTemp) {
            CatAndBrandResVo_CatElemChild child = new CatAndBrandResVo_CatElemChild();
            child.setLabel(brand.getName());
            child.setValue(brand.getId());
            brands.add(child);
        }
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
            List<Category> childrenTemp = categoryMapper.selectByExample(categoryExample2);
            List<CatAndBrandResVo_CatElemChild> children = new ArrayList<>();
            for (Category category1 : childrenTemp) {
                CatAndBrandResVo_CatElemChild child= new CatAndBrandResVo_CatElemChild();
                child.setLabel(category1.getName());
                child.setValue(category1.getId());
                children.add(child);
            }

            catAndBrandResVo_catElem.setChildren(children);
            nestedcategoryList.add(catAndBrandResVo_catElem);
        }
        return nestedcategoryList;
    }

    @Override
    @Transactional
    public int CreateGoods(GoodsCreatedResVo goodsCreatedResVo) {
        Goods goods = goodsCreatedResVo.getGoods();
        GoodsAttribute[] attributes = goodsCreatedResVo.getAttributes();
        GoodsProduct[] products = goodsCreatedResVo.getProducts();
        GoodsSpecification[] specifications = goodsCreatedResVo.getSpecifications();
        GoodsExample goodsNE = new GoodsExample();
        goodsNE.createCriteria().andNameEqualTo(goods.getName());
        List<Goods> goods1 = goodsMapper.selectByExample(goodsNE);
        if(goods1 == null||goods1.isEmpty()){
            goodsMapper.insert(goods);
            List<Goods> goods2 = goodsMapper.selectByExample(goodsNE);
            Goods goods3 = goods2.get(0);
            Integer goods_id = goods3.getId();
            for (GoodsAttribute attribute : attributes) {
                attribute.setGoodsId(goods_id);
                goodsAttributeMapper.insert(attribute);
            }
            for (GoodsProduct product : products) {
                product.setGoodsId(goods_id);
                goodsProductMapper.insert(product);
            }
            for (GoodsSpecification specification : specifications) {
                specification.setGoodsId(goods_id);
                goodsSpecificationMapper.insert(specification);
            }
            return 1;
        }
        return 0;
    }
}
