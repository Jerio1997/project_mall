package com.cskaoyan.mall.bean;

import lombok.Data;

import java.util.List;

@Data
public class CommentListResVo {
    private Integer total;
    private List<Comment> items;
}
