package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

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
        criteria.andDeletedNotEqualTo(true);
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
        criteria.andDeletedNotEqualTo(true);
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
    public int createGoods(GoodsCreatedResVo goodsCreatedResVo) {
        Goods goods = goodsCreatedResVo.getGoods();
        GoodsAttribute[] attributes = goodsCreatedResVo.getAttributes();
        GoodsProduct[] products = goodsCreatedResVo.getProducts();
        GoodsSpecification[] specifications = goodsCreatedResVo.getSpecifications();
        GoodsExample goodsNE = new GoodsExample();
        goodsNE.createCriteria().andNameEqualTo(goods.getName());
        List<Goods> goods1 = goodsMapper.selectByExample(goodsNE);
        if(goods1 == null||goods1.isEmpty()){
            Date date = new Date();
            goods.setAddTime(date);
            goods.setDeleted(false);
            goodsMapper.insert(goods);
            List<Goods> goods2 = goodsMapper.selectByExample(goodsNE);
            Goods goods3 = goods2.get(0);
            Integer goods_id = goods3.getId();
            for (GoodsAttribute attribute : attributes) {
                Date date1 = new Date();
                attribute.setAddTime(date1);
                attribute.setGoodsId(goods_id);
                attribute.setDeleted(false);
                goodsAttributeMapper.insert(attribute);
            }
            for (GoodsProduct product : products) {
                Date date2 = new Date();
                product.setAddTime(date2);
                product.setGoodsId(goods_id);
                product.setDeleted(false);
                goodsProductMapper.insert(product);
            }
            for (GoodsSpecification specification : specifications) {
                Date date3 = new Date();
                specification.setAddTime(date3);
                specification.setGoodsId(goods_id);
                specification.setDeleted(false);
                goodsSpecificationMapper.insert(specification);
            }
            return 1;
        }
        return 0;
    }

    /**
     * 获得商品详情
     * @param id
     * @return
     */
    @Override
    public GoodsDetailReqVo getGoodsDetail(Integer id) {
        //goods
        GoodsDetailReqVo goodsDetailReqVo = new GoodsDetailReqVo();
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andIdEqualTo(id);
        List<Goods> goods = goodsMapper.selectByExample(goodsExample);
        Goods goods1 = goods.get(0);
        goodsDetailReqVo.setGoods(goods1);
        Integer categoryId = goods1.getCategoryId();
        // categoryIds
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andIdEqualTo(categoryId);
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        Category category = categories.get(0);
        Integer[] categoryIds = new Integer[2];
        categoryIds[0] = category.getPid();
        categoryIds[1] = category.getId();
        goodsDetailReqVo.setCategoryIds(categoryIds);
        //attributes
        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        goodsAttributeExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);
        GoodsAttribute[] attributes = goodsAttributes.toArray(new GoodsAttribute[goodsAttributes.size()]);
        goodsDetailReqVo.setAttributes(attributes);
        //
        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        goodsSpecificationExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(goodsSpecificationExample);
        GoodsSpecification[] specifications = goodsSpecifications.toArray(new GoodsSpecification[goodsSpecifications.size()]);
        goodsDetailReqVo.setSpecifications(specifications);

        GoodsProductExample goodsProductExample = new GoodsProductExample();
        goodsProductExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(goodsProductExample);
        GoodsProduct[] products = goodsProducts.toArray(new GoodsProduct[goodsProducts.size()]);
        goodsDetailReqVo.setProducts(products);

        return goodsDetailReqVo;
    }

    @Override
    @Transactional
    public int updateGoods(GoodsCreatedResVo goodsCreatedResVo) {
        Goods goods = goodsCreatedResVo.getGoods();
        GoodsAttribute[] attributes = goodsCreatedResVo.getAttributes();
        GoodsProduct[] products = goodsCreatedResVo.getProducts();
        GoodsSpecification[] specifications = goodsCreatedResVo.getSpecifications();
        GoodsExample goodsNE = new GoodsExample();
        goodsNE.createCriteria().andNameEqualTo(goods.getName());
        List<Goods> goods1 = goodsMapper.selectByExample(goodsNE);
        if(goods1 == null||goods1.isEmpty()||goods.getName().equals(goods1.get(0).getName())){
            Date date = new Date();
            goods.setUpdateTime(date);
            goods.setDeleted(false);
            goodsMapper.updateByPrimaryKey(goods);
            List<Goods> goods2 = goodsMapper.selectByExample(goodsNE);
            Goods goods3 = goods2.get(0);
            Integer goods_id = goods3.getId();

            GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
            goodsAttributeExample.createCriteria().andGoodsIdEqualTo(goods_id);
            goodsAttributeMapper.deleteByExample(goodsAttributeExample);

            GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
            goodsSpecificationExample.createCriteria().andGoodsIdEqualTo(goods_id);
            goodsSpecificationMapper.deleteByExample(goodsSpecificationExample);

            GoodsProductExample goodsProductExample = new GoodsProductExample();
            goodsProductExample.createCriteria().andGoodsIdEqualTo(goods_id);
            goodsProductMapper.deleteByExample(goodsProductExample);

            for (GoodsAttribute attribute : attributes) {
                Date date1 = new Date();
                attribute.setUpdateTime(date1);
                attribute.setGoodsId(goods_id);
                attribute.setDeleted(false);
                goodsAttributeMapper.insert(attribute);
            }
            for (GoodsProduct product : products) {
                Date date2 = new Date();
                product.setUpdateTime(date2);
                product.setGoodsId(goods_id);
                product.setDeleted(false);
                goodsProductMapper.insert(product);
            }
            for (GoodsSpecification specification : specifications) {
                Date date3 = new Date();
                specification.setUpdateTime(date3);
                specification.setGoodsId(goods_id);
                specification.setDeleted(false);
                goodsSpecificationMapper.insert(specification);
            }
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public int deleteGoods(Goods goods) {
        Integer id = goods.getId();
        goods.setUpdateTime(new Date());
        goods.setDeleted(true);
        goodsMapper.updateByPrimaryKey(goods);
        //Attribute
        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        goodsAttributeExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);
        for (GoodsAttribute goodsAttribute : goodsAttributes) {
            goodsAttribute.setUpdateTime(new Date());
            goodsAttribute.setDeleted(true);
            goodsAttributeMapper.updateByPrimaryKey(goodsAttribute);
        }
        //goodsSpecification
        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        goodsSpecificationExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(goodsSpecificationExample);
        for (GoodsSpecification goodsSpecification : goodsSpecifications) {
            goodsSpecification.setUpdateTime(new Date());
            goodsSpecification.setDeleted(true);
            goodsSpecificationMapper.updateByPrimaryKey(goodsSpecification);
        }
        //goodsProduct
        GoodsProductExample goodsProductExample = new GoodsProductExample();
        goodsProductExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(goodsProductExample);
        for (GoodsProduct goodsProduct : goodsProducts) {
            goodsProduct.setUpdateTime(new Date());
            goodsProduct.setDeleted(true);
            goodsProductMapper.updateByPrimaryKey(goodsProduct);
        }
        return 1;
    }
}
