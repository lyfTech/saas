package org.saas.service.system;

import org.saas.dao.domain.SysUser;

import java.util.List;


public interface SysUserService {

    SysUser getUserById(Long userId);

    SysUser getUserByName(String userName);

    List<SysUser> getAllUserInfo(int offset, int limit);

}
