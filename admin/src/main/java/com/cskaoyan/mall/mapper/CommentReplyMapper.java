package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.CommentReply;
import com.cskaoyan.mall.bean.CommentReplyExample;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentReplyMapper {
    long countByExample(CommentReplyExample example);

    int deleteByExample(CommentReplyExample example);

    @Delete({
        "delete from cskaoyan_mall_comment_reply",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cskaoyan_mall_comment_reply (id, comment_id, ",
        "content, add_time, ",
        "update_time, deleted)",
        "values (#{id,jdbcType=INTEGER}, #{commentId,jdbcType=INTEGER}, ",
        "#{content,jdbcType=VARCHAR}, #{addTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleted,jdbcType=BIT})"
    })
    int insert(CommentReply record);

    int insertSelective(CommentReply record);

    List<CommentReply> selectByExample(CommentReplyExample example);

    @Select({
        "select",
        "id, comment_id, content, add_time, update_time, deleted",
        "from cskaoyan_mall_comment_reply",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("com.cskaoyan.mall.mapper.CommentReplyMapper.BaseResultMap")
    CommentReply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommentReply record, @Param("example") CommentReplyExample example);

    int updateByExample(@Param("record") CommentReply record, @Param("example") CommentReplyExample example);

    int updateByPrimaryKeySelective(CommentReply record);

    @Update({
        "update cskaoyan_mall_comment_reply",
        "set comment_id = #{commentId,jdbcType=INTEGER},",
          "content = #{content,jdbcType=VARCHAR},",
          "add_time = #{addTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "deleted = #{deleted,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CommentReply record);
}
