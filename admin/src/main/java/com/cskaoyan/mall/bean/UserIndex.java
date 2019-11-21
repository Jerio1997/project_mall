package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class UserIndex {
    private Integer uncomment;
    private Integer unpaid;
    private Integer unrecv;
    private Integer unship;
    private List<UserIndex> order;
}
