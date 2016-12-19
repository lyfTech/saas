package org.saas.service.system.impl;

import org.saas.common.handle.ResponseHandle;
import org.saas.common.handle.ResponseHandleT;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.dao.domain.SysDepartment;
import org.saas.dao.domain.SysDepartmentExample;
import org.saas.dao.mapper.SysDepartmentMapper;
import org.saas.service.system.SysDeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by gls on 2016/12/19.
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {
    private static final Logger logger = LoggerFactory.getLogger(SysDeptServiceImpl.class);

    @Resource
    private SysDepartmentMapper departmentMapper;

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
}
