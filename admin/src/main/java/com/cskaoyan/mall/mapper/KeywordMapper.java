package com.cskaoyan.mall.mapper;

import com.cskaoyan.mall.bean.Keyword;
import com.cskaoyan.mall.bean.KeywordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface KeywordMapper {
    long countByExample(KeywordExample example);

    int deleteByExample(KeywordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Keyword record);

    int insertSelective(Keyword record);

    List<Keyword> selectByExample(KeywordExample example);

    Keyword selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Keyword record, @Param("example") KeywordExample example);

    int updateByExample(@Param("record") Keyword record, @Param("example") KeywordExample example);

    int updateByPrimaryKeySelective(Keyword record);

    int updateByPrimaryKey(Keyword record);

    int insertSelectiveAndGetId(Keyword keyword);
}
