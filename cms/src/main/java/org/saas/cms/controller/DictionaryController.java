package org.saas.cms.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.saas.service.system.SysPermService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 数据字典
 * @author yuefeng.liu
 */
@Controller
@RequestMapping(value = "/dict")
public class DictionaryController {
    public static final Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    private SysPermService permService;

    @RequiresPermissions({"dict:list"})
    @RequestMapping(value = "/list")
    public String list(Model model){
        return "dictionary/list";
    }


}
