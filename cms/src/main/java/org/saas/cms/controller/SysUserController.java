package org.saas.cms.controller;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.ResponseHandle;
import org.saas.common.mybatis.Page;
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

    @RequiresPermissions({ "user:list" })
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {
        return "user/list";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Page<SysUser> list(@RequestBody Map map){
        int offset = MapUtils.getIntValue(map, "offset");
        int limit = MapUtils.getIntValue(map, "limit");
        String userName = MapUtils.getString(map, "username");
        SysUserExample example = new SysUserExample();
        if (StringUtils.isNotBlank(userName)){
            example.createCriteria().andUserNameLike("%"+userName+"%");
            example.or().andRealNameLike("%"+userName+"%");
            example.or().andMobileLike("%"+userName+"%");
            example.or().andEmailLike("%"+userName+"%");
        }
        PageRequest pageRequest = new PageRequest(offset,limit);
        Page<SysUser> page = userService.getAllUserInfo(example, pageRequest);
        return page;
    }

    @RequestMapping(value = "/changeState", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseHandle changeState(@RequestParam Long id) {
        return userService.changeState(id);
    }

    @RequiresPermissions({ "user:add" })
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "user/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseHandle doAdd(@RequestBody Map map) {
        SysUser user = new SysUser();
        BaseResponseHandle handle = new BaseResponseHandle();
        if (StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword())) {
            handle.setErrorMessage("请输入正确的用户名和初始密码");
            return handle;
        }
        handle = userService.addUser(user);
        if (handle.getIsSuccess()){
            handle.setMessage("新增用户成功");
        }
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
