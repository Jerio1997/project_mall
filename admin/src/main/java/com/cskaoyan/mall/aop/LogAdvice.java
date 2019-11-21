package com.cskaoyan.mall.aop;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.bean.Log;
import com.cskaoyan.mall.mapper.LogMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Component
@Aspect
public class LogAdvice {

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private HttpServletRequest request;

    private Admin admin;
    private Log log;
    private Boolean flag = false;

    @Pointcut("execution(* com.cskaoyan.mall.service..*(..))")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore() {
        log = new Log();
        log.setAddTime(new Date());
        log.setUpdateTime(new Date());
        log.setIp(request.getRemoteAddr());
        Subject subject = SecurityUtils.getSubject();
//        admin = (Admin) subject.getPrincipal();  // 有可能为空，所以先不加入 log

        String requestURI = request.getRequestURI();
        if (requestURI.contains("admin/auth/login")) {     // 登录操作
            flag = true;
            log.setAction("登录");
            log.setType(1);
        } else if (requestURI.contains("admin/auth/logout")) {     // 退出操作
            flag = true;
            log.setAction("退出");
            log.setType(1);
        } else if (requestURI.contains("admin/admin")) {   // 对管理员的操作
            flag = true;
            log.setType(1);
            String[] split = requestURI.split("/");
            String operate = split[split.length-1];
            switch (operate) {
                case "create":
                    log.setAction("创建管理员");
                    break;
                case "update":
                    log.setAction("编辑管理员");
                    break;
                case "delete":
                    log.setAction("删除管理员");
                    break;
            }
        } else if (requestURI.contains("admin/role")) {   // 对管理员角色的操作
            flag = true;
            log.setType(1);
            String[] split = requestURI.split("/");
            String operate = split[split.length-1];
            switch (operate) {
                case "create":
                    log.setAction("创建角色");
                    break;
                case "update":
                    log.setAction("编辑角色");
                    break;
                case "delete":
                    log.setAction("删除角色");
                    break;
            }
        } /*else {
            log.setType(3);
            log.setAction("其他操作");
        }*/

    }

    @AfterThrowing(value = "logPointCut()", throwing = "throwable")
    public void myThrowing(Throwable throwable) {
        // 发生异常时的操作
        if (admin == null) {
            log.setAdmin("未知用户");   // 检验失败登录不能查找出登录的管理员用户名
        }
        if (flag) {
            log.setStatus(false);
            logMapper.insertSelective(log);
        }
    }

    @AfterReturning("logPointCut()")
    public void myAfter() {
        // 未发生异常时的操作
        if (flag) {
//            log.setAdmin(admin.getUsername());
            log.setStatus(true);
            logMapper.insertSelective(log);
        }
    }

}
