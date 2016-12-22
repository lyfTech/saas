package org.saas.cms.controller;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.mybatis.PageRequest;
import org.saas.dao.domain.*;
import org.saas.service.system.SysDeptService;
import org.saas.service.system.SysPermService;
import org.saas.service.system.SysRoleService;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
@RequestMapping(value = "/role")
public class SysRoleController {
    public static final Logger logger = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysDeptService deptService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysPermService permService;

    @RequiresPermissions({"role:list"})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {
        return "role/list";
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Page<SysRole> list(@RequestBody Map map) {
        int offset = MapUtils.getIntValue(map, "offset");
        int limit = MapUtils.getIntValue(map, "limit");
        String rolename = MapUtils.getString(map, "rolename");
        SysRoleExample example = new SysRoleExample();
        if (StringUtils.isNotBlank(rolename)) {
            example.createCriteria().andNameLike("%" + rolename + "%");
            example.or().andDescriptionLike("%" + rolename + "%");
        }
        example.setOrderByClause("sort");
        PageRequest pageRequest = new PageRequest(offset, limit);
        Page<SysRole> page = roleService.queryRolePage(example, pageRequest);
        return page;
    }

    @RequestMapping(value = "/changeState", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseHandle changeState(@RequestParam Long id) {
        return roleService.changeRoleState(id);
    }

    @RequiresPermissions({"role:add"})
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "role/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseHandle add(@ModelAttribute SysRole role) {
        BaseResponseHandle handle = new BaseResponseHandle();
        if (StringUtils.isBlank(role.getName()) || role.getSort() == null) {
            handle.setErrorMessage("页面参数异常");
            return handle;
        }
        handle = roleService.addRole(role);
        if (handle.getIsSuccess()) {
            handle.setMessage("新增角色成功");
        }
        return handle;
    }

    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponseHandleT<SysRole> getUserById(@PathVariable(value = "id") Long id) {
        SingleResponseHandleT<SysRole> handle = roleService.getRoleById(id);
        return handle;
    }

    @RequiresPermissions({"role:edit"})
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable(value = "id") Long id) {
        SingleResponseHandleT<SysRole> singleResponseHandleT = roleService.getRoleById(id);
        if (singleResponseHandleT.getIsSuccess()) {
            model.addAttribute("role", singleResponseHandleT.getResult());
        }
        return "role/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseHandle edit(@ModelAttribute SysRole role) {
        BaseResponseHandle handle = new BaseResponseHandle();
        if (role.getId() == null) {
            handle.setErrorMessage("页面参数异常");
            return handle;
        }
        SingleResponseHandleT<SysRole> handleT = roleService.getRoleById(role.getId());
        if (handleT.getIsSuccess()) {
            SysRole s = handleT.getResult();
            s.setName(role.getName());
            s.setSort(role.getSort());
            s.setStatus(role.getStatus());
            s.setDescription(role.getDescription());
            handle = roleService.updateRole(s);
            if (handle.getIsSuccess()) {
                handle.setMessage("修改角色成功");
            }
        } else {
            handle.setErrorMessage("角色不存在");
        }
        return handle;
    }

    @RequiresPermissions({"role:perm"})
    @RequestMapping(value = "/perm/{id}", method = RequestMethod.GET)
    public String perm(Model model, @PathVariable(value = "id") Long id) {
        SingleResponseHandleT<SysRole> singleResponseHandleT = roleService.getRoleById(id);
        if (singleResponseHandleT.getIsSuccess()) {
            List<SysPerm> allPerm = permService.getUserPerm(null);
            Set<String> hasPerm = permService.getPremByRoleId(singleResponseHandleT.getResult().getId());
            model.addAttribute("role", singleResponseHandleT.getResult());
            model.addAttribute("allPerm", allPerm);
            model.addAttribute("hasPerm", hasPerm);
        }
        return "role/perm";
    }

    @RequiresPermissions({"role:perm"})
    @RequestMapping(value = "/perm", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseHandle saveUserRole(@RequestParam Long roleId, @RequestParam(value = "ids[]") Integer[] ids) {
        BaseResponseHandle handle = new BaseResponseHandle();
        if (roleId == null){
            handle.setErrorMessage("页面参数异常");
            return handle;
        }
        if (ids != null && ids.length > 0) {
            handle = roleService.saveRolePerm(roleId, ids);
        } else {
            handle.setErrorMessage("参数异常");
        }
        return handle;
    }

}
