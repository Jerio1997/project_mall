package com.cskaoyan.mall.exception;

import com.cskaoyan.mall.bean.BaseRespVo;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(AuthorizationException.class)
    public BaseRespVo nopermissions(){
        return BaseRespVo.fail(522,"没有权限");
    }
}
