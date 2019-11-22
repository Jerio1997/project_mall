package com.cskaoyan.mall.controllerwx;

import com.cskaoyan.mall.bean.BaseReqVo;
import com.cskaoyan.mall.bean.Feedback;
import com.cskaoyan.mall.bean.User;
import com.cskaoyan.mall.service.FeedbackService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("wx/feedback")
public class WxFeedbackController {

    @Autowired
    FeedbackService feedbackService;
    @RequestMapping(value = "submit")
    public BaseReqVo feedbackSubmit(@RequestBody Feedback feedback){
        feedback.setAddTime(new Date());
        feedback.setUpdateTime(new Date());
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        String usename = user.getUsername();
        BaseReqVo baseReqVo = new BaseReqVo();
        int status = feedbackService.addFeedback(feedback,usename);
        baseReqVo.setErrmsg("成功");
        baseReqVo.setErrno(0);
        return baseReqVo;
    }
}
