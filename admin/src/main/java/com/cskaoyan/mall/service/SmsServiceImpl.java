package com.cskaoyan.mall.service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.cskaoyan.mall.component.AliyunComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    AliyunComponent aliyunComponent;
    @Override
    public void sendRegCaptcha(String mobile, String code) {
        IAcsClient client = aliyunComponent.getIAcsClient();
        CommonRequest request = aliyunComponent.getRequest();
        request.putQueryParameter("PhoneNumbers",mobile);
        request.putQueryParameter("TemplateParam","{\"code\": \""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}

