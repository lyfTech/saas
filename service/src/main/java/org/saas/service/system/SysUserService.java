package org.saas.service.system;

import org.saas.common.dto.KeyValueDto;
import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.ResponseHandleT;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.mybatis.PageRequest;
import org.saas.dao.domain.SysRole;
import org.saas.dao.domain.SysUser;
import org.saas.dao.domain.SysUserExample;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


public interface SysUserService {

    SingleResponseHandleT<SysUser> getUserById(Long userId);

    SysUser getUserByName(String userName);

    Page<SysUser> getAllUserInfo(SysUserExample example, PageRequest pageRequest);

    Set<String> getRolesByName(String userName);

    Set<String> getPremsByName(String userName);

    BaseResponseHandle addUser(SysUser sysUser);

    BaseResponseHandle changeState(Long id);

    SysUser currentUserInfo();

    BaseResponseHandle updateUser(SysUser user);

    BaseResponseHandle resetPassword(Long id, String password);

    ResponseHandleT<SysRole> getUserRoleByUserId(Long id);

    @Transactional
    BaseResponseHandle saveUserRole(Long userId, Integer[] roleIds);
}
