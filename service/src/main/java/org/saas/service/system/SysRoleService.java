package org.saas.service.system;

import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.mybatis.PageRequest;
import org.saas.dao.domain.SysRole;
import org.saas.dao.domain.SysRoleExample;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by gls on 2016/12/20.
 */
public interface SysRoleService {
    Page<SysRole> queryRolePage(SysRoleExample example, PageRequest pageRequest);

    SingleResponseHandleT<SysRole> getRoleById(Long roleId);

    SysRole getRoleByName(String roleName);

    BaseResponseHandle changeRoleState(Long id);

    BaseResponseHandle addRole(SysRole role);

    BaseResponseHandle updateRole(SysRole role);

    @Transactional
    BaseResponseHandle saveRolePerm(Long roleId, Integer[] permIds);
}
