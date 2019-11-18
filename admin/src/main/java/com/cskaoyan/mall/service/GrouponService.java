package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Groupon;
import com.cskaoyan.mall.bean.GrouponRecordListResVo;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 16:21
 **/
public interface GrouponService {

    GrouponRecordListResVo getGrouponRecord(Integer page, Integer limit, Integer goodsId, String sort, String order);

}
