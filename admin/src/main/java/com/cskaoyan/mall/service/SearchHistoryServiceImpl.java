package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.SearchHistory;
import com.cskaoyan.mall.mapper.SearchHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService{

    @Autowired
    private SearchHistoryMapper searchHistoryMapper;

    @Override
    public List<SearchHistory> selectHistoryKeywordList() {
        return searchHistoryMapper.selectHistoryKeywordList();
    }
}
