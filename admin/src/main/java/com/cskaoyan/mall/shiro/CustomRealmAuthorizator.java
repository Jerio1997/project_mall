/**
 * Created by IntelliJ IDEA.
 * User: BJ
 * Date: 2019/11/20
 * Time: 12:03
 */
package com.cskaoyan.mall.shiro;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

public class CustomRealmAuthorizator extends ModularRealmAuthorizer {


    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        this.assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames(); //获取config中配置的realms
        String realmName = realmNames.iterator().next();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;
            if (realmName.contains("Admin")) {
                return ((AdminRealm) realm).isPermitted(principals, permission);
            }

            if (realmName.contains("Wx")) {
                if (realm instanceof WxRealm) {
                    return ((WxRealm) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        this.assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames(); //获取config中配置的realms
        String realmName = realmNames.iterator().next();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;
            if (realmName.contains("Admin")) {
                return ((AdminRealm) realm).isPermitted(principals, permission);
            }

            if (realmName.contains("Wx")) {
                if (realm instanceof WxRealm) {
                    return ((WxRealm) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        this.assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames(); //获取config中配置的realms
        String realmName = realmNames.iterator().next();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) continue;
            if (realmName.contains("Admin")) {
                return ((AdminRealm) realm).isPermitted(principals, roleIdentifier);
            }

            if (realmName.contains("Wx")) {
                if (realm instanceof WxRealm) {
                    return ((WxRealm) realm).isPermitted(principals, roleIdentifier);
                }
            }
        }
        return false;
    }
}
