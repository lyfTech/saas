package org.saas.cms.controller;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.saas.common.enums.PermTypeEnums;
import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.ResponseHandleT;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.mybatis.PageRequest;
import org.saas.dao.domain.SysPerm;
import org.saas.dao.domain.SysPermExample;
import org.saas.service.system.SysPermService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/perm")
public class SysPermController {
    public static final Logger logger = LoggerFactory.getLogger(SysPermController.class);

    @Autowired
    private SysPermService permService;

    @RequiresPermissions({"perm:list"})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {
        return "perm/list";
    }

    @RequiresPermissions({"perm:list"})
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Page<SysPerm> list(@RequestBody Map map) {
        int offset = MapUtils.getIntValue(map, "offset");
        int limit = MapUtils.getIntValue(map, "limit");
        String code = MapUtils.getString(map, "code");
        SysPermExample example = new SysPermExample();
        if (StringUtils.isNotBlank(code)) {
            example.createCriteria().andNameLike("%" + code + "%");
            example.or().andCodeLike("%" + code + "%");
        }
        example.setOrderByClause("sort");
        PageRequest pageRequest = new PageRequest(offset, limit);
        Page<SysPerm> page = permService.queryPermPage(example, pageRequest);
        return page;
    }

    @RequiresPermissions({"perm:list"})
    @RequestMapping(value = "/changeState", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseHandle changeState(@RequestParam Long id) {
        return permService.changePermState(id);
    }

    @RequiresPermissions({"perm:add"})
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        List<PermTypeEnums> permTypeEnumss = EnumUtils.getEnumList(PermTypeEnums.class);
        ResponseHandleT<SysPerm> allMenu = permService.getAllMenu();
        model.addAttribute("menus", allMenu != null ? allMenu.getResult() : null);
        model.addAttribute("permTypeEnumss", permTypeEnumss);
        return "perm/add";
    }

    @RequiresPermissions({"perm:add"})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseHandle add(@ModelAttribute SysPerm perm) {
        BaseResponseHandle handle = new BaseResponseHandle();
        if (StringUtils.isBlank(perm.getName()) || perm.getCode() == null) {
            handle.setErrorMessage("页面参数异常");
            return handle;
        }
        handle = permService.addPerm(perm);
        if (handle.getIsSuccess()) {
            handle.setMessage("新增菜单成功");
        }
        return handle;
    }

    @RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponseHandleT<SysPerm> getUserById(@PathVariable(value = "id") Long id) {
        SingleResponseHandleT<SysPerm> handle = permService.getPermById(id);
        return handle;
    }

    @RequiresPermissions({"perm:edit"})
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable(value = "id") Long id) {
        SingleResponseHandleT<SysPerm> singleResponseHandleT = permService.getPermById(id);
        if (singleResponseHandleT.getIsSuccess()) {
            List<PermTypeEnums> permTypeEnumss = EnumUtils.getEnumList(PermTypeEnums.class);
            ResponseHandleT<SysPerm> allMenu = permService.getAllMenu();
            model.addAttribute("menus", allMenu != null ? allMenu.getResult() : null);
            model.addAttribute("permTypeEnumss", permTypeEnumss);
            model.addAttribute("perm", singleResponseHandleT.getResult());
        }
        return "perm/edit";
    }

    @RequiresPermissions({"perm:edit"})
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseHandle edit(@ModelAttribute SysPerm perm) {
        BaseResponseHandle handle = new BaseResponseHandle();
        if (perm.getId() == null) {
            handle.setErrorMessage("页面参数异常");
            return handle;
        }
        SingleResponseHandleT<SysPerm> handleT = permService.getPermById(perm.getId());
        if (handleT.getIsSuccess()) {
            SysPerm s = handleT.getResult();
            s.setName(perm.getName());
            s.setCode(perm.getCode());
            s.setParentId(perm.getParentId());
            s.setUrl(perm.getUrl());
            s.setIcon(perm.getIcon());
            s.setType(perm.getType());
            s.setSort(perm.getSort());
            s.setStatus(perm.getStatus());
            s.setDescription(perm.getDescription());
            handle = permService.updateRole(s);
            if (handle.getIsSuccess()) {
                handle.setMessage("修改权限成功");
            }
        } else {
            handle.setErrorMessage("权限不存在");
        }
        return handle;
    }

    @RequestMapping(value = "/icon", method = RequestMethod.GET)
    public String icon() {
        return "perm/icon";
    }
}
