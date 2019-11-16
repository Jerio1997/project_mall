package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 15:52
 **/
@Data
public class TopicListResVo {

    private Integer total;

    private List<Topic> items;
}
