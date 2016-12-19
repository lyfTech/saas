package org.saas.cms.controller;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.ResponseHandle;
import org.saas.common.handle.ResponseHandleT;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.mybatis.PageRequest;
import org.saas.dao.domain.SysDepartment;
import org.saas.dao.domain.SysUser;
import org.saas.dao.domain.SysUserExample;
import org.saas.service.system.SysDeptService;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping(value = "/user")
public class SysUserController {
    public static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysDeptService deptService;

    @RequiresPermissions({"user:list"})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {
        return "user/list";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Page<SysUser> list(@RequestBody Map map) {
        int offset = MapUtils.getIntValue(map, "offset");
        int limit = MapUtils.getIntValue(map, "limit");
        String userName = MapUtils.getString(map, "username");
        SysUserExample example = new SysUserExample();
        if (StringUtils.isNotBlank(userName)) {
            example.createCriteria().andUserNameLike("%" + userName + "%");
            example.or().andRealNameLike("%" + userName + "%");
            example.or().andMobileLike("%" + userName + "%");
            example.or().andEmailLike("%" + userName + "%");
        }
        PageRequest pageRequest = new PageRequest(offset, limit);
        Page<SysUser> page = userService.getAllUserInfo(example, pageRequest);
        return page;
    }

    @RequestMapping(value = "/changeState", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseHandle changeState(@RequestParam Long id) {
        return userService.changeState(id);
    }

    @RequiresPermissions({"user:add"})
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "user/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseHandle doAdd(@ModelAttribute SysUser user) {
        BaseResponseHandle handle = new BaseResponseHandle();
        if (StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword())) {
            handle.setErrorMessage("请输入正确的用户名和初始密码");
            return handle;
        }
        handle = userService.addUser(user);
        if (handle.getIsSuccess()) {
            handle.setMessage("新增用户成功");
        }
        return handle;
    }

    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponseHandleT<SysUser> getUserById(@PathVariable(value = "id") Long id) {
        SingleResponseHandleT<SysUser> handle = userService.getUserById(id);
        return handle;
    }

    @RequiresPermissions({"user:edit"})
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable(value = "id") Long id) {
        SingleResponseHandleT<SysUser> singleResponseHandleT = userService.getUserById(id);
        if (singleResponseHandleT.getIsSuccess()) {
            model.addAttribute("user", singleResponseHandleT.getResult());
            model.addAttribute("depts", deptService.getAll().getResult());
        }
        return "user/edit";
    }


}
