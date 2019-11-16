package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Issue;
import com.cskaoyan.mall.bean.IssueExample;
import com.cskaoyan.mall.mapper.IssueMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IssueServiceImpl implements IssueService{

    @Autowired
    IssueMapper issueMapper;

    @Override
    public Map<String, Object> getIssueList(int page, int limit, String question, String sort, String order) {
        PageHelper.startPage(page, limit);

        IssueExample example = new IssueExample();

        IssueExample.Criteria criteria = example.createCriteria();

        if (question != null) {
            criteria.andQuestionLike("%" + question + "%");
        }
        example.setOrderByClause(sort + " " +  order);
        List<Issue> issueList = issueMapper.selectByExample(example);

        PageInfo<Issue> issuePageInfo = new PageInfo<>(issueList);
        long total = issuePageInfo.getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", issueList);
        return data;
    }

    @Override
    public int deleteIssue(Issue issue) {
        int i = issueMapper.deleteByPrimaryKey(issue.getId());
        return i;
    }

    @Override
    public int addIssue(Issue issue) {
        int i = issueMapper.insertSelectiveAndGetId(issue);
        return i;
    }

    @Override
    public int updateIssue(Issue issue) {
        int i = issueMapper.updateByPrimaryKey(issue);
        return i;
    }
}
