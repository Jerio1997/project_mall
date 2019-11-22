package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.SearchHistory;

import java.util.List;

public interface SearchHistoryService {
    List<SearchHistory> selectHistoryKeywordListByUserId(Integer id);

    void deleteSearchHistoryByUserId(Integer id);

    int addHistoryKeyword(String keyword);
}
