package org.saas.cms.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.saas.common.mybatis.Page;
import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.ResponseHandle;
import org.saas.common.mybatis.PageRequest;
import org.saas.dao.domain.SysUser;
import org.saas.dao.domain.SysUserExample;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping(value = "/user")
public class SysUserController {
    public static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService userService;

    @RequiresPermissions({ "user/list" })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {
        return "user/list";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Page<SysUser> list(Map map){
        SysUserExample example = new SysUserExample();
        PageRequest pageRequest = new PageRequest(1,1);
        Page<SysUser> page = userService.getAllUserInfo(example, pageRequest);
        return page;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponseHandle registe(@RequestParam String userName, @RequestParam String password) {
        BaseResponseHandle handle = new BaseResponseHandle();
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            handle.setErrorMessage("请输入正确的用户名和密码");
            return handle;
        }
        SysUser user = new SysUser(userName, password);
        handle = userService.addUser(user);
        return handle;
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
