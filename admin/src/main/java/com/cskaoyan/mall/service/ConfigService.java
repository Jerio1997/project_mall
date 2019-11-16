package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Express;
import com.cskaoyan.mall.bean.Mall;
import com.cskaoyan.mall.bean.OrderConfig;
import com.cskaoyan.mall.bean.WechatConfig;

public interface ConfigService {


    Mall selectMallInfo();

    int updateMallInfo(Mall mall);

    Express selectExpressInfo();

    int updateExpressInfo(Express express);

    OrderConfig selectOrderInfo();

    int updateOrderConfigInfo(OrderConfig orderConfig);

    WechatConfig selectWechatInfo();

    int updateWechatConfigInfo(WechatConfig wechatConfig);
}
