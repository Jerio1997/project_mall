package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Topic;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 15:50
 **/
public interface TopicService {
    int queryTopicCounts();

    List<Topic> queryTopic(Integer page, Integer limit, String title, String subtitle, String sort, String order);

    int updateTopic(Topic topic);

    int deleteTopic(Topic topic);

    int createTopic(Topic topic);
}
