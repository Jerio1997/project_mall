package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/17 23:13
 **/
@Data
public class StatisticUsers {

    private String[] columns = {"day","users"};

    private List<StatisticProperty> rows;



    public List<StatisticProperty> getRows() {
        return rows;
    }

    public void setRows(List<StatisticProperty> rows) {
        this.rows = rows;
    }
}
