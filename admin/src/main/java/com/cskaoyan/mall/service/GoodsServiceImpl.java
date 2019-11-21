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
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserMapper userMapper;

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
        List<Goods> goods = goodsMapper.selectByExampleWithBLOBs(goodsExample);

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
        List<Goods> goods1 = goodsMapper.selectByExampleWithBLOBs(goodsNE);
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
                product.setId(null);
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
        List<Goods> goods = goodsMapper.selectByExampleWithBLOBs(goodsExample);
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
        List<Goods> goods1 = goodsMapper.selectByExampleWithBLOBs(goodsNE);
        if(goods1 == null||goods1.isEmpty()||goods.getName().equals(goods1.get(0).getName())){
            Date date = new Date();
            goods.setUpdateTime(date);
            goods.setDeleted(false);
            goodsMapper.updateByPrimaryKey(goods);
            List<Goods> goods2 = goodsMapper.selectByExampleWithBLOBs(goodsNE);
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

    @Override
    public List<Goods> getNewGoodsList(int page, int limit) {
        PageHelper.startPage(page, limit);
        GoodsExample example = new GoodsExample();
        example.createCriteria().andIsNewEqualTo(true);
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        return goodsList;
    }

    @Override
    public List<Goods> getHotGoodsList(int page, int limit) {
        PageHelper.startPage(page, limit);
        GoodsExample example = new GoodsExample();
        example.createCriteria().andIsHotEqualTo(true);
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        return goodsList;
    }

    @Override
    public Goods queryGoodsById(Integer goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        return goods;
    }

    @Override
    public List<Goods> queryGoodsByCategoryLevel1(int page, int limit, Integer id) {
        GoodsExample example = new GoodsExample();
        example.createCriteria().andCategoryIdEqualTo(id);
        List<Goods> goodsList = goodsMapper.selectByExample(example);
        if (goodsList == null) {
            goodsList = new ArrayList<>();
        }
        CategoryExample example1 = new CategoryExample();
        example1.createCriteria().andPidEqualTo(id);
        List<Category> categories = categoryMapper.selectByExample(example1);
        for (Category category : categories) {
            GoodsExample example2 = new GoodsExample();
            example2.createCriteria().andCategoryIdEqualTo(category.getId());
            List<Goods> goodsList1 = goodsMapper.selectByExample(example2);
            for (Goods goods : goodsList1) {
                goodsList.add(goods);
            }
        }
        if (limit != 0 && goodsList.size() > limit) {
            List<Goods> goodsLimitList = new ArrayList<>();
            for (int i = 0; i < limit; i++) {
                goodsLimitList.add(goodsList.get(i));
            }
            return goodsLimitList;
        }
        return goodsList;
    }

    @Override
    public List<Category> queryCategoryByGoodsCodition(String keyword, Integer brandId, Boolean isHot, Boolean isNew, Integer page, String sort, String order, Integer size) {
        List<Goods> goodsList = queryGoodsByCondition(keyword, brandId, null, isHot, isNew, page, sort, order, size);
        ArrayList<Category> filterCategoryList = new ArrayList<>();
        HashSet<Integer> categoryIds = new HashSet<>();
        int count = goodsList.size();
        if (count!=0){
            for (Goods goods : goodsList) {
                Integer qCategoryId = goods.getCategoryId();
                categoryIds.add(qCategoryId);
            }
            for (Integer categoryId : categoryIds) {
                Category category = categoryService.getCategoryById(categoryId);
                filterCategoryList.add(category);
            }
        }
        return filterCategoryList;
    }

    @Override
    public List<Goods> queryGoodsByCondition(String keyword,Integer brandId,Integer categoryId,Boolean isHot,Boolean isNew, Integer page,String sort,String order, Integer size) {
        PageHelper.startPage(page,size);
        GoodsExample goodsExample = new GoodsExample();
        if(!StringUtils.isEmpty(sort)&&!StringUtils.isEmpty(order)){
            String sortOrder = sort+" "+order.toUpperCase();
            goodsExample.setOrderByClause(sortOrder);
        }
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        if(!StringUtils.isEmpty(categoryId)&&categoryId != 0){
            criteria.andCategoryIdEqualTo(categoryId);
        }
        if(!StringUtils.isEmpty(isNew)){
            criteria.andIsNewEqualTo(true);
        }
        if(!StringUtils.isEmpty(isHot)){
            criteria.andIsHotEqualTo(true);
        }
        if(!StringUtils.isEmpty(keyword)){
            String value = "%" + keyword + "%";
            criteria.andNameLike(value);
        }

        if(!StringUtils.isEmpty(brandId)){
            criteria.andBrandIdEqualTo(brandId);
        }

        List<Goods> goods = goodsMapper.selectByExampleWithBLOBs(goodsExample);
        return goods;
    }
    // 查询 categoryId 相同的商品并按 sort_order递减排序（大的优先）
    @Override
    public List<Goods> queryRelateGoods(Integer id) {
        Goods goods = this.queryGoodsById(id);
        Integer categoryId = goods.getCategoryId();
        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andCategoryIdEqualTo(categoryId);
        goodsExample.setOrderByClause("sort_order DESC");
        List<Goods> relatedGoods = goodsMapper.selectByExampleWithBLOBs(goodsExample);
        List<Goods> res = new ArrayList<>();
        if(relatedGoods.size()>6){
            for (int i = 0; i <6; i++) {
                Goods goods1 = relatedGoods.get(i);
                res.add(goods1);
            }
        }else{
            res = relatedGoods;
        }
        return res;
    }

    @Override
    public GoodsDetailResVo_Wx queryGoodsDetail(Integer id) {
        GoodsDetailResVo_Wx goodsDetailResVo_wx = new GoodsDetailResVo_Wx();
        Goods goods = queryGoodsById(id);
        Integer categoryId = goods.getCategoryId();
        Integer brandId = goods.getBrandId();
        goodsDetailResVo_wx.setInfo(goods);

        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andValueIdEqualTo(id).andTypeEqualTo((byte) 0);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        int size = comments.size();
        GoodsDetailResVo_Wx.CommentBean commentBean = new GoodsDetailResVo_Wx.CommentBean();
        commentBean.setCount(size);
        List<GoodsDetailResVo_Wx.CommentBean.DataBean> data = new ArrayList<>();

        for (Comment comment : comments) {
            GoodsDetailResVo_Wx.CommentBean.DataBean dataBean = new GoodsDetailResVo_Wx.CommentBean.DataBean();
            dataBean.setAddTime(comment.getAddTime());
            dataBean.setContent(comment.getContent());
            dataBean.setId(comment.getId());
            dataBean.setPicList(comment.getPicUrls());
            User user = userMapper.selectByPrimaryKey(comment.getUserId());
            dataBean.setAvatar(user.getAvatar());
            dataBean.setNickName(user.getNickname());
            data.add(dataBean);
        }

        if(data.size()>2){
            data = data.subList(0, 2);
        }
        commentBean.setData(data);
        goodsDetailResVo_wx.setComment(commentBean);

        Brand brand = brandMapper.selectByPrimaryKey(brandId);
        goodsDetailResVo_wx.setBrand(brand);

        GoodsSpecificationExample goodsSpecificationExample = new GoodsSpecificationExample();
        goodsSpecificationExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationMapper.selectByExample(goodsSpecificationExample);
        List<GoodsDetailResVo_Wx.SpecificationListBean> specificationList = new ArrayList<>();
        HashSet<String> goodsSpecificationName = new HashSet<>();
        for (GoodsSpecification goodsSpecification : goodsSpecifications) {
           goodsSpecificationName.add(goodsSpecification.getSpecification());
        }
        for (String s : goodsSpecificationName) {
            GoodsDetailResVo_Wx.SpecificationListBean specificationListBean = new GoodsDetailResVo_Wx.SpecificationListBean();
            specificationListBean.setName(s);
            List<GoodsSpecification> goodsSpecificationList = new ArrayList<>();
            for (GoodsSpecification goodsSpecification : goodsSpecifications) {
                if(s.equals(goodsSpecification.getSpecification())){
                    goodsSpecificationList.add(goodsSpecification);
                }
            }
            specificationListBean.setValueList(goodsSpecificationList);
            specificationList.add(specificationListBean);
        }


        goodsDetailResVo_wx.setSpecificationList(specificationList);

        GrouponRulesExample grouponRulesExample = new GrouponRulesExample();
        grouponRulesExample.createCriteria().andGoodsIdEqualTo(id);
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(grouponRulesExample);
        goodsDetailResVo_wx.setGroupon(grouponRules);

        IssueExample issueExample = new IssueExample();
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        goodsDetailResVo_wx.setIssue(issues);

        GoodsAttributeExample goodsAttributeExample = new GoodsAttributeExample();
        goodsAttributeExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsAttribute> goodsAttributes = goodsAttributeMapper.selectByExample(goodsAttributeExample);
        goodsDetailResVo_wx.setAttribute(goodsAttributes);

        GoodsProductExample goodsProductExample = new GoodsProductExample();
        goodsProductExample.createCriteria().andGoodsIdEqualTo(id);
        List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(goodsProductExample);
        goodsDetailResVo_wx.setProductList(goodsProducts);

        goodsDetailResVo_wx.setShareImage("");
        goodsDetailResVo_wx.setUserHasCollect(0);
        return goodsDetailResVo_wx;
    }

    @Override
    public Goods getGoodsById(int goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        return goods;
    }

    @Override
    public GoodsProduct getGoodsProductById(int productId) {
        GoodsProduct goodsProduct = goodsProductMapper.selectByPrimaryKey(productId);
        return goodsProduct;
    }
}
