package org.saas.cms.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.saas.common.utils.StringUtils;
import org.saas.dao.domain.SysUser;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义的指定Shiro验证用户登录的类
 */
public class MyRealm extends AuthorizingRealm {
    public static final Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private SysUserService userService;

    // 为当前登陆成功的用户授予权限和角色，已经登陆成功了
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal(); //获取用户名
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.getRolesByName(username));
        authorizationInfo.setStringPermissions(userService.getPremsByName(username));
        return authorizationInfo;
    }

    // 验证当前登录的用户，获取认证信息
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal(); // 获取用户名
        if (StringUtils.isBlank(username)){
            throw new AuthenticationException("数据异常，请重新登录");
        }
        SysUser user = userService.getUserByName(username);
        if(user != null) {
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "myRealm");
            return authcInfo;
        } else {
            return null;
        }
    }
}
