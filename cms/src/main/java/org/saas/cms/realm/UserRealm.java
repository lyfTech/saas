package org.saas.cms.realm;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.saas.common.utils.StringUtils;
import org.saas.dao.domain.SysUser;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义的指定Shiro验证用户登录的类
 */
public class UserRealm extends AuthorizingRealm {
    public static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private SysUserService userService;

    // 为当前登陆成功的用户授予权限和角色，已经登陆成功了
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal(); //获取用户名
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.getRolesByName(username));
        authorizationInfo.setStringPermissions(userService.getPremsByName(username));
        logger.info("用户{}设置权限成功", username);
        return authorizationInfo;
    }

    // 验证当前登录的用户，获取认证信息
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();
//        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
//        String username = upToken.getUsername();
        System.out.println("<-- 系统用户 -->验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        SysUser user = userService.getUserByName(username);
        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        if(user.getStatus() == 2) {
            throw new LockedAccountException(); //帐号锁定
        }

        /* 方法一：带盐值*/
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUserName(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getUserName()+user.getSalt()),//salt=username+salt
                getName()  //realm name
        );
        logger.info("用户{}验证成功", authenticationInfo.getPrincipals());
        return authenticationInfo;
    }


    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}