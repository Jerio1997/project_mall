package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.mapper.CommentMapper;
import com.cskaoyan.mall.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.System;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    UserMapper userMapper;
    @Override
    public int queryCommentCountsByCondition(Integer userId, Integer valueId,Byte type,Boolean hasPicture) {
        CommentExample commentExample = new CommentExample();
        CommentExample.Criteria criteria = commentExample.createCriteria();
        if(!StringUtils.isEmpty(userId)){
            criteria.andUserIdEqualTo(userId);
        }
        if(!StringUtils.isEmpty(valueId)){
            criteria.andValueIdEqualTo(valueId);
        }
        if(!StringUtils.isEmpty(type)){
            criteria.andTypeEqualTo(type);
        }
        if(!StringUtils.isEmpty(hasPicture)){
            criteria.andHasPictureEqualTo(hasPicture);
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

    @Override
    @Transactional
    public int deleteComment(Comment comment) {
        Integer id = comment.getId();
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andIdEqualTo(id);
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        Comment comment1 = comments.get(0);
        comment1.setDeleted(true);
        comment1.setUpdateTime(new Date());
        commentMapper.updateByPrimaryKey(comment1);
        return 1;

    }

    @Override
    public CommentResVo_Wx queryCommentByConditionForWx(Integer valueId, Byte type, Integer showType, Integer page, Integer size) {
        CommentResVo_Wx commentResVo_wx = new CommentResVo_Wx();
        PageHelper.startPage(page,size);
        CommentExample commentExample = new CommentExample();
        String sortOrder = "add_time"+" "+"DESC";
        commentExample.setOrderByClause(sortOrder);
        CommentExample.Criteria criteria = commentExample.createCriteria();
        criteria.andValueIdEqualTo(valueId).andTypeEqualTo(type);
        if(!StringUtils.isEmpty(showType)&&showType!=0){
            criteria.andHasPictureEqualTo(true);
        }
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        int count = comments.size();
        commentResVo_wx.setCount(count);
        commentResVo_wx.setCurrentPage(page);
        List<CommentResVo_Wx.DataBean> data = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResVo_Wx.DataBean dataBean = new CommentResVo_Wx.DataBean();
            dataBean.setAddTime(comment.getAddTime());
            dataBean.setContent(comment.getContent());
            dataBean.setPicList(comment.getPicUrls());
            User user = userMapper.selectByPrimaryKey(comment.getUserId());
            CommentResVo_Wx.DataBean.UserInfoBean userInfoBean = new CommentResVo_Wx.DataBean.UserInfoBean();
            userInfoBean.setNickName(user.getNickname());
            userInfoBean.setAvatarUrl(user.getAvatar());
            dataBean.setUserInfo(userInfoBean);
            data.add(dataBean);
        }
        commentResVo_wx.setData(data);
        return commentResVo_wx;
    }

    @Override
    public CommentCountReqVo_Wx queryCommentCountByConditionForWx(Integer valueId, Byte type) {
        CommentCountReqVo_Wx commentCountReqVo_wx = new CommentCountReqVo_Wx();
        int all = queryCommentCountsByCondition(null, valueId, type, null);
        int hasPicture = queryCommentCountsByCondition(null, valueId, type, true);
        commentCountReqVo_wx.setAllCount(all);
        commentCountReqVo_wx.setHasPicCount(hasPicture);
        return commentCountReqVo_wx;
    }

    @Override
    @Transactional
    public Comment postComment(Integer userId,Comment comment) {
        comment.setUserId(userId);
        comment.setAddTime(new Date());
        comment.setDeleted(false);
        comment.setId(null);
        int insert = commentMapper.insert(comment);
        System.out.println("insert = " + insert);
        CommentExample commentExample = new CommentExample();
        commentExample.setOrderByClause("id"+ " " + "DESC");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        Comment commentRes = new Comment();
        if(comments.isEmpty()){
             commentRes = commentMapper.selectByPrimaryKey(1);
        }else{
            commentRes = commentMapper.selectByPrimaryKey(comments.get(0).getId());
        }
        return commentRes;
    }
}
