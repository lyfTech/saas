package org.saas.service.system;

import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.ResponseHandleT;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.mybatis.PageRequest;
import org.saas.dao.domain.SysDepartment;
import org.saas.dao.domain.SysDepartmentExample;

/**
 * Created by gls on 2016/12/19.
 */
public interface SysDeptService {
    ResponseHandleT<SysDepartment> getAll();

    SingleResponseHandleT<SysDepartment> getDepartmentById(Long id);

    Page<SysDepartment> queryPermPage(SysDepartmentExample example, PageRequest pageRequest);

    ResponseHandleT<SysDepartment> getDeptByExample(SysDepartmentExample example);

    BaseResponseHandle changeState(Long id);

    BaseResponseHandle updateDept(SysDepartment dept);

    BaseResponseHandle addDept(SysDepartment dept);
}
