package org.saas.cms.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.saas.cms.handle.ResponseHandle;
import org.saas.dao.domain.SysUser;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping(value = "/")
public class LoginController {
    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SysUserService userService;

    @RequestMapping(value = "login")
    public String login(HttpServletRequest request, HttpServletResponse response, String userName, String password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            subject.login(token);
            return "framework/index";
        } catch (Exception e) {
            e.getMessage();
        }
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandle<SysUser> getUserById(@PathVariable(value = "id") Long id) {
        SysUser user = userService.getUserById(id);
        if (user != null) {
            logger.info("===============" + user.getUserName());
            return new ResponseHandle<SysUser>(true, user);
        }
        return new ResponseHandle<SysUser>(false, new SysUser());
    }


}
