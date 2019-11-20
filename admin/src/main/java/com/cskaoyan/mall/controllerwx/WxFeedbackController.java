package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Feedback;
import com.cskaoyan.mall.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("wx/feedback")
public class WxFeedbackController {

    @Autowired
    FeedbackService feedbackService;
    @RequestMapping(value = "submit")
    public BaseReqVo feedbackSubmit(@RequestBody Feedback feedback){
        feedback.setAddTime(new Date());
        feedback.setUpdateTime(new Date());
        BaseReqVo baseReqVo = new BaseReqVo();
        int statu = feedbackService.addFeedback(feedback);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        baseReqVo.setData(feedback);
        return baseReqVo;
    }
}
