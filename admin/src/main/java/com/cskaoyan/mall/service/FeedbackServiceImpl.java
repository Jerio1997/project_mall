package com.cskaoyan.mall.service;

import com.cskaoyan.mall.bean.Feedback;
import com.cskaoyan.mall.bean.FeedbackExample;
import com.cskaoyan.mall.mapper.FeedbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    FeedbackMapper feedbackMapper;
    @Override
    public int addFeedback(Feedback feedback) {
        FeedbackExample feedbackExample = new FeedbackExample();
        //feedbackExample.createCriteria().andUsernameEqualTo(feedback.getUsername());
        List<Feedback> feedbackList = feedbackMapper.selectByExample(feedbackExample);
        Integer status;
        if (feedbackList == null || feedbackList.size() == 0){
            status = 0;
        }else {
            status = feedbackList.get(0).getStatus();
        }
        feedback.setStatus(status);
        int i = feedbackMapper.insertSelective(feedback);
        return i;
    }
}
