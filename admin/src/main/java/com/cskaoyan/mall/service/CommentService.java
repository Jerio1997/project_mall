package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Comment;
import com.cskaoyan.mall.bean.Goods;

import java.util.List;

public interface CommentService {
    int queryCommentCounts(Integer userId,Integer valueId);

    List<Comment> queryComment(Integer page, Integer limit,Integer userId,Integer valueId, String sort, String order);

    int deleteComment(Comment comment);
}
