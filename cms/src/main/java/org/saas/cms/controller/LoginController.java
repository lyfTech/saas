package org.saas.cms.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
public class LoginController {
    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SysUserService userService;

    @RequestMapping(value = "/")
    public String index(){
        return "forward:index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()){
            return "redirect:index";
        }
        return "login";
    }

    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doLogin(HttpServletRequest request, @RequestParam(value = "username") String userName, @RequestParam String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            map.put("content", "请输入正确的用户名和密码");
            return map;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(true);

        //获取当前Subject
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,SystemAuthorizingRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + userName + "]进行登录验证..验证开始");
            currentUser.login(token);
            map.put("type","success");
            //获取登录前的Url
            String url = "/cms/index";
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            if (savedRequest != null) url = savedRequest.getRequestUrl();
            map.put("redirectUrl", url);
            logger.info("对用户[" + userName + "]进行登录验证..验证通过");
        } catch (UnknownAccountException uae) {
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,未知账户");
            map.put("content",  "未知账户");
        } catch (IncorrectCredentialsException ice) {
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误的凭证");
            map.put("content",  "密码不正确");
        } catch (LockedAccountException lae) {
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,账户已锁定");
            map.put("content",  "账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,错误次数过多");
            map.put("content",  "用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            logger.info("对用户[" + userName + "]进行登录验证..验证未通过,堆栈轨迹如下");
            ae.printStackTrace();
            map.put("content",  "用户名或密码不正确");
        }
        //验证是否登录成功
        if (currentUser.isAuthenticated()) {
            logger.info("用户[" + userName + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
        } else {
            token.clear();
        }
        return map;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ModelAndView logout() {
        if (SecurityUtils.getSubject().getSession() != null) {
            SecurityUtils.getSubject().logout();
            logger.info("用户登出成功");
        }
        return new ModelAndView("login");
    }


}
