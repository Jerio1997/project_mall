package com.cskaoyan.mall.shiro;

import com.cskaoyan.mall.bean.Admin;
import com.cskaoyan.mall.mapper.UserMapper;
import com.cskaoyan.mall.service.AuthService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AdminRealm extends AuthorizingRealm {
    @Autowired
    AuthService authService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String username = usernamePasswordToken.getUsername();
        Admin admin = authService.selectAdminByUsername(username);
        String passwordFromDb = admin.getPassword();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(admin,passwordFromDb,getName());
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Admin admin = (Admin) principalCollection.getPrimaryPrincipal();
        String username = admin.getUsername();
        List<String> permissions = authService.selectPermissionsByUsername(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissions);
        return authorizationInfo;
    }

}
