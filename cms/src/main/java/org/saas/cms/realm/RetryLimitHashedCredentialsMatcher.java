package org.saas.cms.realm;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;


/** 
 * @ClassName: RetryLimitHashedCredentialsMatcher 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhangp 
 * @date 2016年6月17日 上午11:06:04 
 *  
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    private static final Logger logger = LoggerFactory.getLogger(RetryLimitHashedCredentialsMatcher.class);

    private Cache<String, AtomicInteger> passwordRetryCache;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if(retryCount.incrementAndGet() > 5) {
            //if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            logger.info("用户{}验证成功", username);
            //clear retry count
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
