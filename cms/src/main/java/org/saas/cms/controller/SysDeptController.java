package org.saas.cms.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.saas.common.handle.ResponseHandleT;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.dao.domain.SysDepartment;
import org.saas.service.system.SysDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/dept")
public class SysDeptController {
    public static final Logger logger = LoggerFactory.getLogger(SysDeptController.class);

    @Autowired
    private SysDeptService deptService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String index() {
        return "dept/list";
    }

/*    @RequestMapping(value = "list", method = RequestMethod.POST)
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
        Page<SysUser> page = deptService.getAllUserInfo(example, pageRequest);
        return page;
    }*/


    @RequiresPermissions({"dept:add"})
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "dept/add";
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


}
