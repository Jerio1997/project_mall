package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.CommentReply;
import com.cskaoyan.mall.bean.CommentReplyExample;
import com.cskaoyan.mall.mapper.CommentReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class CommentReplyServiceImpl implements CommentReplyService {
    @Autowired
    CommentReplyMapper commentReplyMapper;
    @Override
    public int addCommentReply(Integer commentId, String content) {
        CommentReplyExample commentReplyExample = new CommentReplyExample();
        commentReplyExample.createCriteria().andCommentIdEqualTo(commentId);
        List<CommentReply> commentReplies = commentReplyMapper.selectByExample(commentReplyExample);
        if(commentReplies == null||commentReplies.isEmpty()){
            CommentReply commentReply = new CommentReply();
            commentReply.setAddTime(new Date());
            commentReply.setCommentId(commentId);
            commentReply.setContent(content);
            commentReply.setDeleted(false);
            commentReplyMapper.insert(commentReply);
            return 1;
        }
        return 0;
    }
}
