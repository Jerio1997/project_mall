package com.cskaoyan.mall.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Author Jerio
 * CreateTime 2019/11/17 23:14
 **/
@Data
public class StatisticProperty {

    private String day;

    private int users;
}
