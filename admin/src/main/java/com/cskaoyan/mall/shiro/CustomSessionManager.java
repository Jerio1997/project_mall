package com.cskaoyan.mall.shiro;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class CustomSessionManager extends DefaultWebSessionManager {
    @Override
    protected Serializable getSessionId(ServletRequest servletRequest, ServletResponse response) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String adminHeader = request.getHeader("X-Litemall-Admin-Token");
        String wxHeader = request.getHeader("X-cskaoyanmall-Admin-Token");
        if (adminHeader != null && !"".equals(adminHeader)){
            return adminHeader;
        }else if (wxHeader != null && !"".equals(wxHeader)){
            return wxHeader;
        }else {
            return super.getSessionId(request, response);
        }

    }
}
