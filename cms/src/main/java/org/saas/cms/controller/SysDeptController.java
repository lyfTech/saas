package org.saas.cms.controller;

import org.apache.commons.collections.MapUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.ResponseHandleT;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.mybatis.PageRequest;
import org.saas.common.utils.StringUtils;
import org.saas.dao.domain.SysDepartment;
import org.saas.dao.domain.SysDepartmentExample;
import org.saas.dao.domain.SysUser;
import org.saas.service.system.SysDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping(value = "/dept")
public class SysDeptController {
    public static final Logger logger = LoggerFactory.getLogger(SysDeptController.class);

    @Autowired
    private SysDeptService deptService;

    @RequiresPermissions({"dept:list"})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {
        return "dept/list";
    }

    @RequiresPermissions({"dept:list"})
    @RequestMapping(value = "list", method = RequestMethod.POST)
    @ResponseBody
    public Page<SysDepartment> list(@RequestBody Map map){
        int offset = MapUtils.getIntValue(map, "offset");
        int limit = MapUtils.getIntValue(map, "limit");
        String deptInfo = MapUtils.getString(map, "deptInfo");
        SysDepartmentExample example = new SysDepartmentExample();
        if (StringUtils.isNotBlank(deptInfo)){
            example.createCriteria().andNameLike("%"+deptInfo+"%");
            example.or().andDescriptionLike("%"+deptInfo+"%");
        }
        PageRequest pageRequest = new PageRequest(offset,limit);
        Page<SysDepartment> page = deptService.queryPermPage(example, pageRequest);
        return page;
    }


    @RequiresPermissions({"dept:add"})
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "dept/add";
    }

    @RequiresPermissions({"dept:add"})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponseHandle doAdd(@ModelAttribute SysDepartment department) {
        BaseResponseHandle handle = new BaseResponseHandle();
        if (org.apache.commons.lang3.StringUtils.isBlank(department.getName())) {
            handle.setErrorMessage("请输入正确的部门名称");
            return handle;
        }
        handle = deptService.addDept(department);
        if (handle.getIsSuccess()) {
            handle.setMessage("新增部门成功");
        }
        return handle;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHandleT<SysDepartment> getAll() {
        ResponseHandleT<SysDepartment> handleT = deptService.getAll();
        if (handleT.getIsSuccess()) {
            return handleT;
        }
        return handleT;
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponseHandleT<SysDepartment> getDeptById(@PathVariable(value = "id") Long id) {
        SingleResponseHandleT<SysDepartment> handleT = deptService.getDepartmentById(id);
        if (handleT.getIsSuccess()) {
            return handleT;
        }
        return handleT;
    }

    @RequestMapping(value = "/open-dept-list", method = RequestMethod.GET)
    public String openDeptList() {
        return "dept/open-dept-list";
    }

    @RequestMapping(value = "/open-user-list", method = RequestMethod.GET)
    public String openUserList() {
        return "dept/open-user-list";
    }
}
