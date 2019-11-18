package com.cskaoyan.mall.controller;

import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.TopicListResVo;
import com.cskaoyan.mall.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        TopicListResVo topicListResVo;
        topicListResVo = topicService.queryTopic(page,limit,title,subtitle,sort,order);
        BaseReqVo<TopicListResVo> baseReqVo = new BaseReqVo<>();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(topicListResVo);
        return baseReqVo;
    }

    @PostMapping("update")
    public BaseReqVo updateTopic(@RequestBody Topic topic){
        topic.setUpdateTime(new Date());
        int result = topicService.updateTopic(topic);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(topic);
        return baseReqVo;
    }

    @PostMapping("delete")
    public BaseReqVo deleteTopic(@RequestBody Topic topic){
        int result = topicService.deleteTopic(topic);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        return baseReqVo;
    }

    @PostMapping("create")
    public BaseReqVo createTopic(@RequestBody Topic topic){
        int result = topicService.createTopic(topic);
        BaseReqVo baseReqVo = new BaseReqVo();
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(topic);
        return baseReqVo;
    }
}
