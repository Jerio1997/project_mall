package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Comment;
import com.cskaoyan.mall.bean.CommentCountReqVo_Wx;
import com.cskaoyan.mall.bean.CommentResVo_Wx;
import com.cskaoyan.mall.bean.Goods;

import java.util.List;

public interface CommentService {
    int queryCommentCountsByCondition(Integer userId, Integer valueId,Byte type,Boolean hasPicture);

    List<Comment> queryComment(Integer page, Integer limit,Integer userId,Integer valueId, String sort, String order);

    int deleteComment(Comment comment);

    CommentResVo_Wx queryCommentByConditionForWx(Integer valueId, Byte type, Integer showType, Integer page, Integer size);

    CommentCountReqVo_Wx queryCommentCountByConditionForWx(Integer valueId, Byte type);

    Comment postComment(Comment comment);
}
