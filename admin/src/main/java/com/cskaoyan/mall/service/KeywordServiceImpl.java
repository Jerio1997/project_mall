package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Keyword;
import com.cskaoyan.mall.bean.KeywordExample;
import com.cskaoyan.mall.bean.SearchHistory;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.mapper.KeywordMapper;
import com.cskaoyan.mall.mapper.SearchHistoryMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    private KeywordMapper keywordMapper;

    @Autowired
    private SearchHistoryMapper searchHistoryMapper;

    @Override
    public Map<String, Object> getKeyword(int page, int limit, String keyword, String url, String sort, String order) {
        PageHelper.startPage(page, limit);

        KeywordExample example = new KeywordExample();
        KeywordExample.Criteria criteria = example.createCriteria();

        if (keyword != null) {
            criteria.andKeywordLike("%" + keyword + "%");
        }

        if (url != null) {
            criteria.andUrlLike("%" + url + "%");
        }

        example.setOrderByClause(sort + " " + order);
        List<Keyword> keywordList = keywordMapper.selectByExample(example);

        PageInfo<Keyword> keywordPageInfo = new PageInfo<>(keywordList);
        long total = keywordPageInfo.getTotal();

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", keywordList);
        return data;
    }

    @Override
    public int addKeyword(Keyword keyword) {
        int i = keywordMapper.insertSelectiveAndGetId(keyword);
        return i;
    }

    @Override
    public int updateKeyword(Keyword keyword) {
        int i = keywordMapper.updateByPrimaryKey(keyword);
        return i;
    }

    @Override
    public int deleteKeyword(Keyword keyword) {
        int i = keywordMapper.deleteByPrimaryKey(keyword.getId());
        return i;
    }

    @Override
    public Keyword selectDefaultKeyword(Integer defaultKeywordId) {
        return keywordMapper.selectByPrimaryKey(defaultKeywordId);
    }

    @Override
    public List<Keyword> selectHotKeyWordList() {
        KeywordExample example = new KeywordExample();
        example.createCriteria().andIsHotEqualTo(true);
        return keywordMapper.selectByExample(example);
    }

    @Override
    public List<String> selectKeywordStringList(String keyword, User user) {
        if (user != null) {   // 将历史记录插入搜索历史
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setAddTime(new Date());
            searchHistory.setUpdateTime(new Date());
            searchHistory.setUserId(user.getId());
            searchHistory.setKeyword(keyword);
            searchHistory.setFrom("wx");
            searchHistoryMapper.insert(searchHistory);
        }
        return keywordMapper.selectKeywordStringList("%" + keyword + "%");
    }
}
