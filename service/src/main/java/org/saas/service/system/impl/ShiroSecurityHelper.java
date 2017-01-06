package org.saas.service.system.impl;
import java.util.Collection;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.saas.common.utils.StringUtils;
import org.saas.dao.domain.SysUser;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dylan
 * @time 2013-8-12
 */
public class ShiroSecurityHelper {

    private final static Logger log = LoggerFactory.getLogger(ShiroSecurityHelper.class);

    private static SysUserService userService;

    private static SessionDAO sessionDAO;

    /**
     * 获得当前用户名
     *
     * @return
     */
    public static String getCurrentUsername() {
        Subject subject = getSubject();
        PrincipalCollection collection = subject.getPrincipals();
        if (null != collection && !collection.isEmpty()) {
            return (String) collection.iterator().next();
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     *
     * @return
     */
    public static String getSessionId() {
        Session session = getSession();
        if (null == session) {
            return null;
        }
        return getSession().getId().toString();
    }

    /**
     * @param username
     * @return
     */
    public static Session getSessionByUsername(String username){
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for(Session session : sessions){
            if(null != session && StringUtils.equals(String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)), username)){
                return session;
            }
        }
        return null;
    }



    /**
     * 判断当前用户是否已通过认证
     * @return
     */
    public static boolean hasAuthenticated() {
        return getSubject().isAuthenticated();
    }

    private static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
}
