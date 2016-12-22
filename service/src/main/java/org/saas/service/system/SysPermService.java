package org.saas.service.system;

import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.ResponseHandleT;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.mybatis.PageRequest;
import org.saas.dao.domain.SysPerm;
import org.saas.dao.domain.SysPermExample;

import java.util.List;
import java.util.Set;

public interface SysPermService {
    List<SysPerm> getUserPerm(String username);

    Set<String> getPremByRoleId(Long roleId);

    Page<SysPerm> queryPermPage(SysPermExample example, PageRequest pageRequest);

    SingleResponseHandleT<SysPerm> getPermById(Long permId);

    SysPerm getPermByCode(String code);

    BaseResponseHandle changePermState(Long id);

    BaseResponseHandle addPerm(SysPerm perm);

    BaseResponseHandle updateRole(SysPerm perm);

    ResponseHandleT<SysPerm> getAllMenu();
}
