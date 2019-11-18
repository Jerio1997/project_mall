package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.bean.TopicExample;
import com.cskaoyan.mall.bean.TopicListResVo;
import com.cskaoyan.mall.mapper.TopicMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
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
    public TopicListResVo queryTopic(Integer page, Integer limit, String title, String subtitle, String sort, String order) {
        TopicListResVo topicListResVo = new TopicListResVo();
        PageHelper.startPage(page,limit);
        String orderByClause = sort + " " + order.toUpperCase();
        TopicExample topicExample = new TopicExample();
        topicExample.setOrderByClause(orderByClause);
        TopicExample.Criteria criteria = topicExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        if(!StringUtils.isEmpty(title)){
            criteria.andTitleLike("%" + title + "%");
        }
        if (!StringUtils.isEmpty(subtitle)) {
            criteria.andSubtitleLike("%" + subtitle + "%");
        }
        List<Topic> topics = topicMapper.selectByExample(topicExample);
        topicListResVo.setItems(topics);
        topicListResVo.setTotal(topics.size());
        return topicListResVo;
    }

    @Override
    public int updateTopic(Topic topic) {
        int result = topicMapper.updateByPrimaryKey(topic);
        return result;
    }

    @Override
    public int deleteTopic(Topic topic) {
        topic.setDeleted(true);
        topic.setUpdateTime(new Date());
        int result = topicMapper.updateByPrimaryKey(topic);
        return result;
    }

    @Override
    public int createTopic(Topic topic) {
        TopicExample example = new TopicExample();
        example.setOrderByClause("id desc");
        List<Topic> topicList = topicMapper.selectByExample(example);
        Integer id;
        if(topicList == null || topicList.size() == 0){
            id = 0;
        } else {
            id = topicList.get(0).getId();
        }
        topic.setId(++id);
        int result = topicMapper.insertSelective(topic);
        return result;
    }
}
