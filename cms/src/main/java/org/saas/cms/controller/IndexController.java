package org.saas.cms.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.saas.dao.domain.SysPerm;
import org.saas.service.system.SysPermService;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class IndexController {
    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysPermService permService;

    @RequestMapping(value = "/index")
    public String index(Model model){
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()){
            List<SysPerm> userPerm = permService.getUserPerm((String) currentUser.getPrincipal());
            model.addAttribute("menu", userPerm);
            return "index-h-ui";
        }
        return "redirect:login";
    }


}
