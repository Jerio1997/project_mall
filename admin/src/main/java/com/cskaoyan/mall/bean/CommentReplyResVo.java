package com.cskaoyan.mall.bean;

import lombok.Data;

@Data
public class CommentReplyResVo {

    /**
     * commentId : 1077
     * content : fad
     */

    private int commentId;
    private String content;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
