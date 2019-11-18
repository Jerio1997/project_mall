package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/15 21:53
 **/
@Data
public class AdListResVo {

    private Integer total;

    private List<Ad> items;
}
