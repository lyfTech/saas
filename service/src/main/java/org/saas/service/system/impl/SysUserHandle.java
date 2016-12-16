package org.saas.service.system.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.saas.common.enums.ConsantEnums;
import org.saas.common.utils.StringUtils;
import org.saas.dao.domain.SysUser;

/**
 * 用户辅助类
 */
public class SysUserHandle {

    public SysUser getCurrentUserInfo() {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isNoneBlank(userName)) {
            org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            session.getAttribute(ConsantEnums.CURRENT_USERINFO.getKey());
        }
        return null;
    }
}
