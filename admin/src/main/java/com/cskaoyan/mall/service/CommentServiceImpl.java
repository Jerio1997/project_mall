package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Comment;
import com.cskaoyan.mall.bean.CommentExample;
import com.cskaoyan.mall.bean.Goods;
import com.cskaoyan.mall.bean.GoodsExample;
import com.cskaoyan.mall.mapper.CommentMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Override
    public int queryCommentCounts(Integer userId, Integer valueId) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(valueId);
        }
        criteria.andDeletedNotEqualTo(true);
        long total = commentMapper.countByExample(commentExample);
        return (int) total;
    }

    @Override
    public List<Comment> queryComment(Integer page, Integer limit, Integer userId, Integer valueId, String sort, String order) {
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause(orderByClause);
        CommentExample.Criteria criteria = commentExample.createCriteria();
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(valueId);
        }
        criteria.andDeletedNotEqualTo(true);
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        return comments;
    }
}
