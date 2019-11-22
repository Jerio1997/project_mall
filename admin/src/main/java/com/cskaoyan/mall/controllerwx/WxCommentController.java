package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.*;
import com.cskaoyan.mall.service.CommentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("wx/comment")
@RestController
public class WxCommentController {
    @Autowired
    CommentService commentService;

    /**
     * 获取评论
     * @param valueId
     * @param type
     * @param showType
     * @param page
     * @param size
     * @return
     */
    @GetMapping("list")
    public BaseReqVo<CommentResVo_Wx> getCommentList(Integer valueId, Byte type, Integer showType, Integer page, Integer size){
        BaseReqVo<CommentResVo_Wx> baseReqVo = new BaseReqVo<>();
        CommentResVo_Wx commentResVo_wx = new CommentResVo_Wx();
        commentResVo_wx = commentService.queryCommentByConditionForWx(valueId,type,showType,page,size);
        baseReqVo.setData(commentResVo_wx);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @GetMapping("count")
    public BaseReqVo getCommentCount(Integer valueId,Byte type){
        BaseReqVo<CommentCountReqVo_Wx> baseReqVo = new BaseReqVo<>();
        CommentCountReqVo_Wx commentCountReqVo_wx = commentService.queryCommentCountByConditionForWx(valueId,type);
        baseReqVo.setData(commentCountReqVo_wx);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @PostMapping("post")
    public BaseReqVo<Comment> postComment(@RequestBody Comment comment){
        BaseReqVo<Comment> baseReqVo = new BaseReqVo<>();
        Subject subject = SecurityUtils.getSubject();
        User principal = ((User) subject.getPrincipal());
        Integer userId = principal.getId();
        Comment res = commentService.postComment(userId,comment);
        baseReqVo.setData(res);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }
}
