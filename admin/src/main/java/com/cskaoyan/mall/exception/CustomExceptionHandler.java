package com.cskaoyan.mall.exception;

import com.cskaoyan.mall.bean.BaseRespVo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(AuthorizationException.class)
    public BaseRespVo nopermissions() {
        return BaseRespVo.fail(522, "没有认证");
    }
    @ExceptionHandler(UnauthorizedException.class)
    public BaseRespVo noauthorized() {
        return BaseRespVo.fail(523, "没有权限");
    }
}
