package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.CommentService;
import com.cskaoyan.mall.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    /**
     * 查询评论
     * @param page
     * @param limit
     * @param userId
     * @param valueId
     * @param sort
     * @param order
     * @return
     */
    @GetMapping("list")
    public BaseReqVo<CommentListResVo> listGoods(Integer page, Integer limit, Integer userId, Integer valueId, String sort, String order){
        BaseReqVo<CommentListResVo> commentResVoBaseReqVo = new BaseReqVo<>();
        int total = commentService.queryCommentCounts(userId, valueId);
        List<Comment> comments = commentService.queryComment(page, limit, userId, valueId, sort, order);
        CommentListResVo commentListResVo = new CommentListResVo();
        commentListResVo.setTotal(total);
        commentListResVo.setItems(comments);
        commentResVoBaseReqVo.setErrno(0);
        commentResVoBaseReqVo.setErrmsg("成功");
        commentResVoBaseReqVo.setData(commentListResVo);
        return commentResVoBaseReqVo;
    }
}
