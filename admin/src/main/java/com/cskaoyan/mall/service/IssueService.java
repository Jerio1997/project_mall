package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Issue;

import java.util.List;
import java.util.Map;

public interface IssueService {
    Map<String, Object> getIssueList(int page, int limit, String question, String sort, String order);

    int addIssue(Issue issue);

    int deleteIssue(Issue issue);

    int updateIssue(Issue issue);
}
