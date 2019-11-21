package com.cskaoyan.mall.config;

import com.cskaoyan.mall.shiro.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    //login → anon匿名
    //index → 认证之后才能访问
    //info
    //success


    /*shiroFilter*/
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        认证失败重定向的url
        shiroFilterFactoryBean.setLoginUrl("/admin/auth/unauthenticated");
        //配置的是拦截器 shiro提供的filter
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //第一个参数是请求url 第二个参数是过滤器
        filterChainDefinitionMap.put("/admin/auth/login","anon");
//        filterChainDefinitionMap.put("/admin/auth/unauthenticated","anon");
        filterChainDefinitionMap.put("/wx/user/login","anon");
        filterChainDefinitionMap.put("/**", "anon");
//        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    /*SecurityManager*/
    @Bean
    public DefaultWebSecurityManager securityManager(AdminRealm adminRealm, WxRealm wxRealm,
                                                     CustomSessionManager sessionManager,
                                                     CustomRealmAuthenticator authenticator,
                                                     CustomRealmAuthorizator authorizer){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        //单个realm

        securityManager.setSessionManager(sessionManager);
        securityManager.setAuthenticator(authenticator);
        securityManager.setAuthorizer(authorizer);
        securityManager.setRealms(realms);

        return securityManager;
    }

    /*声明式鉴权*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){

        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public CustomSessionManager sessionManager(){
        CustomSessionManager customSessionManager = new CustomSessionManager();
        customSessionManager.setDeleteInvalidSessions(true);
        customSessionManager.setGlobalSessionTimeout(60000);
        return customSessionManager;
    }

    @Bean
    public CustomRealmAuthenticator authenticator(AdminRealm adminRealm, WxRealm wxRealm){
        CustomRealmAuthenticator customRealmAuthenticator = new CustomRealmAuthenticator();
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        customRealmAuthenticator.setRealms(realms);
        return customRealmAuthenticator;
    }
    @Bean
    public CustomRealmAuthorizator authorizator(AdminRealm adminRealm, WxRealm wxRealm){
        CustomRealmAuthorizator customRealmAuthorizator = new CustomRealmAuthorizator();
        ArrayList<Realm> realms = new ArrayList<>();
        realms.add(adminRealm);
        realms.add(wxRealm);
        customRealmAuthorizator.setRealms(realms);
        return customRealmAuthorizator;
    }
}
