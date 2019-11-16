package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Keyword;

import java.util.Map;

public interface KeywordService {
    Map<String, Object> getKeyword(int page, int limit, String keyword, String url, String sort, String order);

    int addKeyword(Keyword keyword);

    int updateKeyword(Keyword keyword);

    int deleteKeyword(Keyword keyword);
}
