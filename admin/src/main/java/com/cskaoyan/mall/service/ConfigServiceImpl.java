package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.bean.System;
import com.cskaoyan.mall.mapper.SystemMapper;
import com.cskaoyan.mall.utils.RegexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    SystemMapper systemMapper;


    @Override
    public Mall selectMallInfo() {
        Mall mall = new Mall();

        System mallName = systemMapper.selectByPrimaryKey(6);
        mall.setLitemall_mall_name(mallName.getKeyValue());

        System mallQQ = systemMapper.selectByPrimaryKey(8);
        mall.setLitemall_mall_qq(mallQQ.getKeyValue());

        System mallPhone = systemMapper.selectByPrimaryKey(12);
        mall.setLitemall_mall_phone(mallPhone.getKeyValue());

        System mallAddress = systemMapper.selectByPrimaryKey(14);
        mall.setLitemall_mall_address(mallAddress.getKeyValue());

        return mall;
    }

    @Override
    public int updateMallInfo(Mall mall) {
        Date date = new Date();
        System system = new System();

        String mallName = mall.getLitemall_mall_name();

        String mallPhone = mall.getLitemall_mall_phone();

        String mallQQ = mall.getLitemall_mall_qq();

        String mallAddress = mall.getLitemall_mall_address();

        if ("".equals(mallName) || "".equals(mallAddress) || "".equals(mallPhone) || "".equals(mallQQ)) {
            return -1;
        }
        if((!RegexUtils.checkMobile(mallPhone))||(!RegexUtils.checkPhone(mallPhone))){
            return -2;
        }
        if(!RegexUtils.checkChinese(mallAddress)){
            return -3;
        }
        if(!RegexUtils.checkqq(mallQQ)){
            return -4;
        }

        SystemExample mallNameExample = new SystemExample();
        mallNameExample.createCriteria().andIdEqualTo(6);
        system.setKeyValue(mallName);
        system.setUpdateTime(date);


        systemMapper.updateByExampleSelective(system, mallNameExample);

        SystemExample mallPhoneExample = new SystemExample();
        mallPhoneExample.createCriteria().andIdEqualTo(12);
        system.setKeyValue(mallPhone);


        systemMapper.updateByExampleSelective(system, mallPhoneExample);


        SystemExample mallQQExample = new SystemExample();
        mallQQExample.createCriteria().andIdEqualTo(8);
        system.setKeyValue(mallQQ);


        systemMapper.updateByExampleSelective(system, mallQQExample);


        SystemExample mallAddressExample = new SystemExample();
        mallAddressExample.createCriteria().andIdEqualTo(14);
        system.setKeyValue(mallAddress);


        systemMapper.updateByExampleSelective(system, mallAddressExample);


        return 0;
    }

    @Override
    public Express selectExpressInfo() {
        Express express = new Express();
        System expressMin = systemMapper.selectByPrimaryKey(5);
        express.setLitemall_express_freight_min(expressMin.getKeyValue());//满减运费的最低消费

        System expressValue = systemMapper.selectByPrimaryKey(7);
        express.setLitemall_express_freight_value(expressValue.getKeyValue());//运费满减不足所需运费
        return express;
    }

    @Override
    public int updateExpressInfo(Express express) {
        Date date = new Date();
        String expressMin = express.getLitemall_express_freight_min();//运费满减所需最低消费
        String expressValue = express.getLitemall_express_freight_value();//运费满减不足所需运费


        System system = new System();
        system.setUpdateTime(date);
        //修改 运费满减所需最低消费
        SystemExample expressMinExample = new SystemExample();
        expressMinExample.createCriteria().andIdEqualTo(5);
        system.setKeyValue(expressMin);
        systemMapper.updateByExampleSelective(system, expressMinExample);
        //修改 运费满减不足所需运费
        SystemExample expressValueExample = new SystemExample();
        expressValueExample.createCriteria().andIdEqualTo(7);
        system.setKeyValue(expressValue);
        systemMapper.updateByExampleSelective(system, expressValueExample);
        return 0;
    }

    @Override
    public OrderConfig selectOrderInfo() {
        OrderConfig orderConfig = new OrderConfig();
        System unpaid = systemMapper.selectByPrimaryKey(1);
        orderConfig.setLitemall_order_unpaid(unpaid.getKeyValue());//用户未付款，则订单自动取消

        System unconfirm = systemMapper.selectByPrimaryKey(3);
        orderConfig.setLitemall_order_unconfirm(unconfirm.getKeyValue());//未确认收货，则订单自动确认收货

        System comment = systemMapper.selectByPrimaryKey(10);
        orderConfig.setLitemall_order_comment(comment.getKeyValue());//未评价商品，则取消评价资格

        return orderConfig;
    }

    @Override
    public int updateOrderConfigInfo(OrderConfig orderConfig) {
        Date date = new Date();
        String comment = orderConfig.getLitemall_order_comment();//未评价商品，则取消评价资格
        String unconfirm = orderConfig.getLitemall_order_unconfirm();//未确认收货，则订单自动确认收货
        String unpaid = orderConfig.getLitemall_order_unpaid();//用户未付款，则订单自动取消


        System system = new System();
        system.setUpdateTime(date);

        //修改 未评价商品，则取消评价资格 功能对应数据库的值
        SystemExample commentExample = new SystemExample();
        commentExample.createCriteria().andIdEqualTo(10);
        system.setKeyValue(comment);
        systemMapper.updateByExampleSelective(system, commentExample);

        //修改 未确认收货，则订单自动确认收货 功能对应数据库的值
        SystemExample unconfirmExample = new SystemExample();
        unconfirmExample.createCriteria().andIdEqualTo(3);
        system.setKeyValue(unconfirm);
        systemMapper.updateByExampleSelective(system, unconfirmExample);

        //修改 用户未付款，则订单自动取消 功能对应数据库的值
        SystemExample unpaidExample = new SystemExample();
        unpaidExample.createCriteria().andIdEqualTo(1);
        system.setKeyValue(unpaid);
        systemMapper.updateByExampleSelective(system, unpaidExample);

        return 0;
    }

    @Override
    public WechatConfig selectWechatInfo() {
        WechatConfig wechatConfig = new WechatConfig();

        System indexNew = systemMapper.selectByPrimaryKey(2);
        wechatConfig.setLitemall_wx_index_new(indexNew.getKeyValue());

        System share = systemMapper.selectByPrimaryKey(4);
        wechatConfig.setLitemall_wx_share(share.getKeyValue());

        System indexHot = systemMapper.selectByPrimaryKey(9);
        wechatConfig.setLitemall_wx_index_hot(indexHot.getKeyValue());

        System catlogGoods = systemMapper.selectByPrimaryKey(11);
        wechatConfig.setLitemall_wx_catlog_goods(catlogGoods.getKeyValue());

        System catlogList = systemMapper.selectByPrimaryKey(13);
        wechatConfig.setLitemall_wx_catlog_list(catlogList.getKeyValue());

        System indexBrand = systemMapper.selectByPrimaryKey(15);
        wechatConfig.setLitemall_wx_index_brand(indexBrand.getKeyValue());

        System indexTopic = systemMapper.selectByPrimaryKey(16);
        wechatConfig.setLitemall_wx_index_topic(indexTopic.getKeyValue());

        return wechatConfig;
    }

    @Override
    public int updateWechatConfigInfo(WechatConfig wechatConfig) {
        Date date = new Date();
        String catlogGoods = wechatConfig.getLitemall_wx_catlog_goods();
        String catlogList = wechatConfig.getLitemall_wx_catlog_list();
        String indexBrand = wechatConfig.getLitemall_wx_index_brand();
        String indexHot = wechatConfig.getLitemall_wx_index_hot();
        String indexNew = wechatConfig.getLitemall_wx_index_new();
        String indexTopic = wechatConfig.getLitemall_wx_index_topic();
        String wxShare = wechatConfig.getLitemall_wx_share();

        System system = new System();
        system.setUpdateTime(date);

        SystemExample catlogGoodsExample = new SystemExample();
        catlogGoodsExample.createCriteria().andIdEqualTo(11);
        system.setKeyValue(catlogGoods);
        systemMapper.updateByExampleSelective(system, catlogGoodsExample);

        SystemExample catlogListExample = new SystemExample();
        catlogListExample.createCriteria().andIdEqualTo(13);
        system.setKeyValue(catlogList);
        systemMapper.updateByExampleSelective(system, catlogListExample);

        SystemExample indexBrandExample = new SystemExample();
        indexBrandExample.createCriteria().andIdEqualTo(15);
        system.setKeyValue(indexBrand);
        systemMapper.updateByExampleSelective(system, indexBrandExample);

        SystemExample indexHotExample = new SystemExample();
        indexHotExample.createCriteria().andIdEqualTo(9);
        system.setKeyValue(indexHot);
        systemMapper.updateByExampleSelective(system, indexHotExample);

        SystemExample indexNewExample = new SystemExample();
        indexNewExample.createCriteria().andIdEqualTo(2);
        system.setKeyValue(indexNew);
        systemMapper.updateByExampleSelective(system, indexNewExample);

        SystemExample indexTopicExample = new SystemExample();
        indexTopicExample.createCriteria().andIdEqualTo(16);
        system.setKeyValue(indexTopic);
        systemMapper.updateByExampleSelective(system, indexTopicExample);

        SystemExample wxShareExample = new SystemExample();
        wxShareExample.createCriteria().andIdEqualTo(4);
        system.setKeyValue(wxShare);
        systemMapper.updateByExampleSelective(system, wxShareExample);

        return 0;
    }
}
