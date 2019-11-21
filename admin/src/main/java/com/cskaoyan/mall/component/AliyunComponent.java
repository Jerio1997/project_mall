package com.cskaoyan.mall.component;

import com.aliyun.oss.OSSClient;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mall.aliyun")
public class AliyunComponent {
    String accessKeyId;
    String accessSecret;
    OssComponent oss;
    SmsComponent sms;
    public OSSClient getOssClient(){
        return new OSSClient(oss.getEndPoint(),accessKeyId,accessSecret);
    }
    public IAcsClient getIAcsClient(){
        DefaultProfile profile = DefaultProfile.getProfile(sms.getRegionId(), accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
    public CommonRequest getRequest(){
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", sms.getRegionId());
        request.putQueryParameter("SignName",sms.getSignName());
        request.putQueryParameter("TemplateCode", sms.getTemplateCode());
//        request.putQueryParameter("TemplateParam", "{\"code\": \"65536\"}");
        return request;
    }

}

