package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.bean.TopicExample;
import com.cskaoyan.mall.bean.TopicListResVo;
import com.cskaoyan.mall.mapper.TopicMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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
        topic.setAddTime(new Date());
        int result = topicMapper.insertSelective(topic);
        return result;
    }

    @Override
    public Map<String, Object> queryTopicOnWx(Integer page, Integer size) {
        Map<String,Object> map = new HashMap<>();
        PageHelper.startPage(page,size);
        TopicExample topicExample = new TopicExample();
        TopicExample.Criteria criteria = topicExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Topic> topics = topicMapper.selectByExample(topicExample);
        map.put("data",topics);
        map.put("count",topics.size());
        return map;
    }

    @Override
    public Map<String, Object> getDetailOfTopic(Integer id) {
        Map<String,Object> map = new HashMap<>();
        Topic topic = topicMapper.selectByPrimaryKey(id);
        map.put("topic",topic);
        map.put("goods",topic.getGoods());
        return map;
    }

    @Override
    public List<Topic> getRelatedTopic(Integer id) {
        TopicExample example = new TopicExample();
        TopicExample.Criteria criteria = example.createCriteria();
        criteria.andIdNotEqualTo(id);
        criteria.andDeletedEqualTo(false);
        List<Topic> topicList = topicMapper.selectByExample(example);
        if(topicList.size()<4){
            return topicList;
        }
        //随机返回4个
        List<Topic> myTopicList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int ran = random.nextInt(topicList.size());
            Topic topic = topicList.get(ran);
            myTopicList.add(topic);
            topicList.remove(ran);
        }
        return myTopicList;
    }


}
