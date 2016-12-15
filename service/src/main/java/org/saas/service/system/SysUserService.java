package org.saas.service.system;

import org.saas.common.mybatis.Page;
import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.mybatis.PageRequest;
import org.saas.dao.domain.SysUser;
import org.saas.dao.domain.SysUserExample;

import java.util.List;
import java.util.Set;


public interface SysUserService {

    SysUser getUserById(Long userId);

    SysUser getUserByName(String userName);

    Page<SysUser> getAllUserInfo(SysUserExample example, PageRequest pageRequest);

    Set<String> getRolesByName(String userName);

    Set<String> getPremsByName(String userName);

    BaseResponseHandle addUser(SysUser sysUser);

}
