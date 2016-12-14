package org.saas.cms.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {
    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SysUserService userService;

    @RequestMapping(value = "/index")
    public String index(){
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()){
            return "index-h-ui";
        }
        return "redirect:login";
    }


}
