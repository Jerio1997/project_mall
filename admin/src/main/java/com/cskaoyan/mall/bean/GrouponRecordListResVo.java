package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/18 21:36
 **/
@Data
public class GrouponRecordListResVo {

    int total;

    List<GrouponRecord> items;

}
