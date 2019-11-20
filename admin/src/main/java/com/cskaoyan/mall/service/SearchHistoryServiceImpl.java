package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.SearchHistory;
import com.cskaoyan.mall.bean.SearchHistoryExample;
import com.cskaoyan.mall.mapper.SearchHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService{

    @Autowired
    private SearchHistoryMapper searchHistoryMapper;

    @Override
    public List<SearchHistory> selectHistoryKeywordList() {
        return searchHistoryMapper.selectHistoryKeywordList();
    }




    @Override
    public int addHistoryKeyword(String keyword) {
        SearchHistoryExample searchHistoryExample = new SearchHistoryExample();
        searchHistoryExample.createCriteria().andKeywordEqualTo(keyword);
        List<SearchHistory> searchHistories = searchHistoryMapper.selectByExample(searchHistoryExample);
        int res = searchHistories.size();
        if(res == 0){
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setUserId(1);
            searchHistory.setAddTime(new Date());
            searchHistory.setDeleted(false);
            searchHistory.setKeyword(keyword);
            searchHistory.setFrom("wx");
            int insert = searchHistoryMapper.insert(searchHistory);
            return insert;
        }else{
            for (SearchHistory searchHistory : searchHistories) {
                searchHistory.setUpdateTime(new Date());
                searchHistoryMapper.updateByPrimaryKey(searchHistory);
            }
           return 2;
        }


    }
}
