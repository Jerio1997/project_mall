package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.SearchHistory;

import java.util.List;

public interface SearchHistoryService {
    List<SearchHistory> selectHistoryKeywordList();

    void deleteSearchHistory();

    int addHistoryKeyword(String keyword);
}
