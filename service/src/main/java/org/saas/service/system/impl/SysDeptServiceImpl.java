package org.saas.service.system.impl;

import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.ResponseHandleT;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.mybatis.PageRequest;
import org.saas.dao.domain.SysDepartment;
import org.saas.dao.domain.SysDepartmentExample;
import org.saas.dao.domain.SysPerm;
import org.saas.dao.domain.SysPermExample;
import org.saas.dao.mapper.SysDepartmentMapper;
import org.saas.service.system.SysDeptService;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

/**
 * Created by gls on 2016/12/19.
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {
    private static final Logger logger = LoggerFactory.getLogger(SysDeptServiceImpl.class);

    @Resource
    private SysDepartmentMapper departmentMapper;
    @Resource
    private SysUserService userService;

    public ResponseHandleT<SysDepartment> getAll() {
        ResponseHandleT<SysDepartment> handleT = new ResponseHandleT<SysDepartment>();
        SysDepartmentExample example = new SysDepartmentExample();
        List<SysDepartment> departments = departmentMapper.selectByExample(example);
        handleT.setResult(departments);
        return handleT;
    }

    public SingleResponseHandleT<SysDepartment> getDepartmentById(Long id) {
        SingleResponseHandleT<SysDepartment> handle = new SingleResponseHandleT<SysDepartment>();
        if (id != null && id > 0) {
            SysDepartment department = departmentMapper.selectByPrimaryKey(id);
            handle.setResult(department);
        } else {
            handle.setErrorMessage("参数异常：请输入正确的DepartmentId");
        }
        return handle;
    }

    public Page<SysDepartment> queryPermPage(SysDepartmentExample example, PageRequest pageRequest) {
        List<SysDepartment> roles = departmentMapper.selectByExampleWithRowbounds(example, pageRequest.getRowBounds());
        int i = departmentMapper.countByExample(example);
        Page<SysDepartment> page = new Page<SysDepartment>(roles, pageRequest, i);
        return page;
    }

    public ResponseHandleT<SysDepartment> getDeptByExample(SysDepartmentExample example) {
        ResponseHandleT<SysDepartment> handleT = new ResponseHandleT<SysDepartment>();
        if (example != null ) {
            List<SysDepartment> departments = departmentMapper.selectByExample(example);
            handleT.setResult(departments);
        }
        return handleT;
    }

    public BaseResponseHandle updateDept(SysDepartment dept) {
        BaseResponseHandle handle = new BaseResponseHandle();
        if (dept.getId() == null){
            handle.setErrorMessage("参数错误");
            return handle;
        }
        int i = departmentMapper.updateByPrimaryKey(dept);
        if (i < 1){
            handle.setErrorMessage("更新失败，请刷新列表");
        }
        return handle;
    }

    public BaseResponseHandle addDept(SysDepartment dept) {
        BaseResponseHandle handle = new BaseResponseHandle();
        try {
            SysDepartmentExample example = new SysDepartmentExample();
            example.createCriteria().andNameEqualTo(dept.getName());
            ResponseHandleT<SysDepartment> deptByExample = getDeptByExample(example);
            if (deptByExample != null) {
                handle.setErrorMessage("部门已经存在");
                return handle;
            }
            dept.setCreateTime(Calendar.getInstance().getTime());
            dept.setCreator(userService.currentUserInfo().getId().toString());
            dept.setModifyTime(Calendar.getInstance().getTime());
            dept.setModifier(userService.currentUserInfo().getId().toString());
            int i = departmentMapper.insert(dept);
            if (i > 0) {
                handle.setMessage("部门添加成功");
                logger.info("部门[{}]添加成功：", dept.getName());
            }
        } catch (Exception e) {
            handle.setErrorMessage("部门添加失败");
            logger.error("部门[{}]添加异常，失败信息：" + e.getMessage(), dept.getName());
        }
        return handle;
    }
}
