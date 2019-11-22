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
    public List<SearchHistory> selectHistoryKeywordListByUserId(Integer id) {
        SearchHistoryExample example = new SearchHistoryExample();
        example.createCriteria().andUserIdEqualTo(id);
        return searchHistoryMapper.selectByExample(example);
    }




    @Override
    public int addHistoryKeyword(Integer userId,String keyword) {
        SearchHistoryExample searchHistoryExample = new SearchHistoryExample();
        searchHistoryExample.createCriteria().andKeywordEqualTo(keyword).andUserIdEqualTo(userId).andDeletedNotEqualTo(true);
        List<SearchHistory> searchHistories = searchHistoryMapper.selectByExample(searchHistoryExample);
        int res = searchHistories.size();
        if(res == 0){
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setUserId(userId);
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

    @Override
    public void deleteSearchHistoryByUserId(Integer id) {
        SearchHistoryExample example = new SearchHistoryExample();
        example.createCriteria().andUserIdEqualTo(id);
        searchHistoryMapper.deleteByExample(example);
    }

}
