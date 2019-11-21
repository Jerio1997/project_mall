package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.bean.TopicListResVo;

import java.util.List;
import java.util.Map;

/**
 * Author Jerio
 * CreateTime 2019/11/16 15:50
 **/
public interface TopicService {
    int queryTopicCounts();

    TopicListResVo queryTopic(Integer page, Integer limit, String title, String subtitle, String sort, String order);

    int updateTopic(Topic topic);

    int deleteTopic(Topic topic);

    int createTopic(Topic topic);

    Map<String, Object> queryTopicOnWx(Integer page, Integer size);

    Map<String, Object> getDetailOfTopic(Integer id);

    List<Topic> getRelatedTopic(Integer id);
}
