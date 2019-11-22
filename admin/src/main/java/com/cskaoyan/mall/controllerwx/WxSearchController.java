package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Keyword;
import com.cskaoyan.mall.bean.SearchHistory;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.service.KeywordService;
import com.cskaoyan.mall.service.SearchHistoryService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx/search")
public class WxSearchController {

    private static final Integer DEFAULT_KEYWORD_ID = 6;

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private SearchHistoryService searchHistoryService;

    @RequestMapping("index")
    public BaseReqVo<Map<String, Object>> searchIndex() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Keyword defaultKeyword = keywordService.selectDefaultKeyword(DEFAULT_KEYWORD_ID);
        List<SearchHistory> historyKeywordList = null;
        if (user != null) {
            historyKeywordList = searchHistoryService.selectHistoryKeywordListByUserId(user.getId());
        }
        List<Keyword> hotKeywordList = keywordService.selectHotKeyWordList();
        HashMap<String, Object> map = new HashMap<>();
        map.put("defaultKeyword", defaultKeyword);
        map.put("historyKeywordList", historyKeywordList);
        map.put("hotKeywordList", hotKeywordList);

        BaseReqVo<Map<String, Object>> baseReqVo = new BaseReqVo<>();
        baseReqVo.setData(map);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("helper")
    public BaseReqVo<List<String>> searchHelper(@RequestParam("keyword") String keyword, HttpSession session) {
        BaseReqVo<List<String>> baseReqVo = new BaseReqVo<>();
        User user = (User) session.getAttribute("user");
        List<String> keywordStringList = keywordService.selectKeywordStringList(keyword, user);
        baseReqVo.setData(keywordStringList);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }

    @RequestMapping("clearhistory")
    public BaseReqVo clearSearchHistory() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        BaseReqVo baseReqVo = new BaseReqVo();
        searchHistoryService.deleteSearchHistoryByUserId(user.getId());
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
