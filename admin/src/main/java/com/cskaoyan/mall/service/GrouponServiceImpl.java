package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.*;
import com.cskaoyan.mall.utils.OrderStatusUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author Jerio
 * CreateTime 2019/11/16 16:21
 **/

@Service
public class GrouponServiceImpl implements GrouponService{

    @Autowired
    GrouponMapper grouponMapper;

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Autowired
    OrderGoodsMapper orderGoodsMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    GoodsProductMapper goodsProductMapper;


    @Override
    public GrouponRecordListResVo getGrouponRecord(Integer page, Integer limit, Integer goodsId, String sort, String order) {
        GrouponRecordListResVo grouponRecordListResVo = new GrouponRecordListResVo();
        List<GrouponRecord> grouponRecordList = new ArrayList<>();
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        GrouponExample grouponExample = new GrouponExample();
        grouponExample.setOrderByClause(orderByClause);
        GrouponExample.Criteria criteria = grouponExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Groupon> grouponList = grouponMapper.selectByExample(grouponExample);
        int total = grouponList.size();
        for (Groupon groupon : grouponList) {
            GrouponRecord grouponRecord = new GrouponRecord();
            grouponRecord.setGroupon(groupon);
            grouponRecord.setRules(grouponRulesMapper.selectByPrimaryKey(groupon.getRulesId()));
            criteria.andGrouponIdEqualTo(groupon.getId());
            List<Groupon> subGroupons = grouponMapper.selectByExample(grouponExample);
            grouponRecord.setSubGroupons(subGroupons);
            OrderGoods orderGoods = orderGoodsMapper.selectByOrderId(groupon.getOrderId());
            Goods goods = goodsMapper.selectByPrimaryKey(orderGoods.getGoodsId());
            grouponRecord.setGoods(goods);
            grouponRecordList.add(grouponRecord);
        }
        grouponRecordListResVo.setItems(grouponRecordList);
        grouponRecordListResVo.setTotal(total);
        return grouponRecordListResVo;
    }

    @Override
    public Map<String, Object> listGroupon(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        GrouponExample example = new GrouponExample();
        GrouponExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Groupon> grouponList = grouponMapper.selectByExample(example);
        List list = new ArrayList();
        for (Groupon groupon : grouponList) {
            Map<String,Object> mapCur = new HashMap<>();
            GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(groupon.getRulesId());
            mapCur.put("groupon_price",grouponRules.getDiscount());
            Goods goods = goodsMapper.selectByPrimaryKey(grouponRules.getGoodsId());
            mapCur.put("goods",goods);
            mapCur.put("groupon_member",grouponRules.getDiscountMember());
            list.add(mapCur);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("data",list);
        map.put("count",list.size());
        return map;
    }

    @Override
    public Map<String, Object> queryMyGroupon(Integer showType) {
        Map<String,Object> map = new HashMap<>();
        if(showType == 1){
            //表示是发起的团购
        } else {
            //表示是参与的团购
        }


        return map;
    }

    @Override
    public Map<String, Object> getDetailOfGrouponById(Integer grouponId) {
        Map<String,Object> map = new HashMap<>();
        Groupon groupon = grouponMapper.selectByPrimaryKey(grouponId);
        User user = userMapper.selectByPrimaryKey(groupon.getCreatorUserId());
        map.put("creator",user);
        map.put("groupon",groupon);
        //拼接joiners， where 参与的团购Id = grouponId
        List<User> joinsers = new ArrayList<>();
        GrouponExample example = new GrouponExample();
        GrouponExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andGrouponIdEqualTo(grouponId);
        List<Groupon> groupons = grouponMapper.selectByExample(example);
        for (Groupon grouponCur : groupons) {
            User userCur = userMapper.selectByPrimaryKey(grouponCur.getUserId());
            joinsers.add(userCur);
        }
        map.put("joiners",joinsers);
        Order order = orderMapper.selectByPrimaryKey(groupon.getOrderId());
        order.setOrderStatusText(OrderStatusUtils.getTextByCode(order.getOrderStatus()));
        order.setHandleOption(new HandleOption(order));
        map.put("orderInfo",order);
        //拼接orderGoods
        //缺少 retailPrice-零售价和 goodsSpecificationValues-规格list
        //retailPrice 在Goods表   goodsSpecificationValues在Goods_product表
        //先通过orderId—>order_goods表   order_goods.goodsId->Goods表  goodsId->Goods_product表
        OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
        OrderGoodsExample.Criteria criteria1 = orderGoodsExample.createCriteria();
        criteria1.andDeletedEqualTo(false);
        criteria1.andOrderIdEqualTo(order.getId());
        List<OrderGoods> orderGoodsList = orderGoodsMapper.selectByExample(orderGoodsExample);
        for (OrderGoods orderGoods : orderGoodsList) {
            Goods goods = goodsMapper.selectByPrimaryKey(orderGoods.getGoodsId());
            orderGoods.setRetailPrice(goods.getRetailPrice());
            GoodsProductExample exampleCur = new GoodsProductExample();
            GoodsProductExample.Criteria criteriaCur = exampleCur.createCriteria();
            criteriaCur.andDeletedEqualTo(false);
            criteriaCur.andGoodsIdEqualTo(goods.getId());
            List<GoodsProduct> goodsProducts = goodsProductMapper.selectByExample(exampleCur);
            List<String[]> list = new ArrayList<>();
            for (GoodsProduct goodsProduct : goodsProducts) {
                list.add(goodsProduct.getSpecifications());
            }
            orderGoods.setGoodsSpecificationValues(list);
        }

        map.put("orderGoods",orderGoodsList);
        //拼接rules 和 linkGrouponId
        GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(groupon.getRulesId());
        map.put("rules",grouponRules);
        map.put("linkGrouponId",groupon.getGrouponId());
        return map;
    }
}
