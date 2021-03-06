package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Groupon;
import com.cskaoyan.mall.bean.GrouponRecordListResVo;
import com.cskaoyan.mall.bean.OrderId;

import java.util.List;
import java.util.Map;

/**
 * Author Jerio
 * CreateTime 2019/11/16 16:21
 **/
public interface GrouponService {

    GrouponRecordListResVo getGrouponRecord(Integer page, Integer limit, Integer goodsId, String sort, String order);

    Map<String, Object> listGroupon(Integer page, Integer size);

    Map<String, Object> queryMyGroupon(Integer showType,Integer id);

    Map<String, Object> getDetailOfGrouponById(Integer grouponId);

    List<Groupon> selectGrouponByRuleId(Integer id);

    int insertGroupon(Groupon groupon,Integer id);

    int queryGrouponNumber(Integer orderId);
}
