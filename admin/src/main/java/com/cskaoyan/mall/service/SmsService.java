package com.cskaoyan.mall.service;

public interface SmsService {
    /*String sendRegCaptcha(String phoneNumber);*/
    void sendRegCaptcha(String mobile, String code);
}
