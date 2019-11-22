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
    public int addFeedback(Feedback feedback, String usename) {
        FeedbackExample feedbackExample = new FeedbackExample();
        feedback.setUsername(usename);
        int i = feedbackMapper.insertSelective(feedback);
        return i;
    }
}
