package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 16:23
 **/
@Data
public class GrouponListResVo {
    private Integer total;
    private List<Groupon> items;
}
