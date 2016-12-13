package org.saas.service.system;

import org.saas.common.BaseResponseHandle;
import org.saas.dao.domain.SysUser;

import java.util.List;
import java.util.Set;


public interface SysUserService {

    SysUser getUserById(Long userId);

    SysUser getUserByName(String userName);

    List<SysUser> getAllUserInfo(int offset, int limit);

    Set<String> getRolesByName(String userName);

    Set<String> getPremsByName(String userName);

    BaseResponseHandle addUser(SysUser sysUser);

}
