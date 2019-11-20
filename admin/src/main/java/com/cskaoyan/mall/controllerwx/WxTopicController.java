package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Topic;
import com.cskaoyan.mall.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author Jerio
 * CreateTime 2019/11/20 11:44
 **/

@RestController
@RequestMapping("wx/topic")
public class WxTopicController {

    @Autowired
    TopicService topicService;

    @GetMapping("list")
    public BaseReqVo listTopic(Integer page,Integer size){
        BaseReqVo baseReqVo = new BaseReqVo();
        Map<String,Object> map = topicService.queryTopicOnWx(page,size);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        return baseReqVo;
    }

    @GetMapping("detail")
    public BaseReqVo detailTopic(Integer id){
        BaseReqVo<Map> baseReqVo = new BaseReqVo<>();
        Map<String,Object> map = topicService.getDetailOfTopic(id);
        Topic topic = (Topic) map.get("topic");
        if(topic.getDeleted()){
            baseReqVo.setErrno(777);
            baseReqVo.setErrmsg("该专题已删除");
            return baseReqVo;
        }
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(map);
        return baseReqVo;
    }

    @GetMapping("related")
    public BaseReqVo relatedTopic(Integer id){
        BaseReqVo<List> baseReqVo = new BaseReqVo<>();
        List<Topic> topicList = topicService.getRelatedTopic(id);
        baseReqVo.setErrno(0);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setData(topicList);
        return baseReqVo;
    }
}
