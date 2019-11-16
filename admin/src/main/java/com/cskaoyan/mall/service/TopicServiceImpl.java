package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.bean.TopicExample;
import com.cskaoyan.mall.mapper.TopicMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 15:50
 **/

@Service
public class TopicServiceImpl implements TopicService{

    @Autowired
    TopicMapper topicMapper;

    @Override
    public int queryTopicCounts() {
        TopicExample topicExample = new TopicExample();
        long total = topicMapper.countByExample(topicExample);
        return (int) total;
    }

    @Override
    public List<Topic> queryTopic(Integer page, Integer limit, String title, String subtitle, String sort, String order) {
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        TopicExample topicExample = new TopicExample();
        topicExample.setOrderByClause(orderByClause);
        TopicExample.Criteria criteria = topicExample.createCriteria();
        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike(title);
        }
        if (!StringUtils.isEmpty(subtitle)) {
            criteria.andSubtitleLike(subtitle);
        }
        List<Topic> topics = topicMapper.selectByExample(topicExample);
        return topics;
    }
}
