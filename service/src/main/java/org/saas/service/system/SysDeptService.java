package org.saas.service.system;

import org.saas.common.handle.ResponseHandleT;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.dao.domain.SysDepartment;

/**
 * Created by gls on 2016/12/19.
 */
public interface SysDeptService {
    ResponseHandleT<SysDepartment> getAll();

    SingleResponseHandleT<SysDepartment> getDepartmentById(Long id);
}
