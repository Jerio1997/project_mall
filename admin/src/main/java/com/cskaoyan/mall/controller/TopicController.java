package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.TopicListResVo;
import com.cskaoyan.mall.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author Jerio
 * CreateTime 2019/11/16 15:49
 **/

@RestController
@RequestMapping("admin/topic")
public class TopicController {

    @Autowired
    TopicService topicService;

    @GetMapping("list")
    public BaseReqVo<TopicListResVo> listTopic (Integer page, Integer limit, String title, String subtitle, String sort, String order){
        BaseReqVo<TopicListResVo> topicListResVoBaseReqVo = new BaseReqVo<>();
        int total = topicService.queryTopicCounts();
        List<Topic> topics = topicService.queryTopic(page,limit,title,subtitle,sort,order);
        TopicListResVo topicListResVo = new TopicListResVo();
        topicListResVo.setTotal(total);
        topicListResVo.setItems(topics);
        topicListResVoBaseReqVo.setErrno(0);
        topicListResVoBaseReqVo.setErrmsg("成功");
        topicListResVoBaseReqVo.setData(topicListResVo);
        return topicListResVoBaseReqVo;
    }
}
