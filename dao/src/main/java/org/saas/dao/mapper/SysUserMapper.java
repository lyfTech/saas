package org.saas.dao.mapper;

import org.saas.common.mybatis.Mapper;
import org.saas.dao.domain.RelUserRole;
import org.saas.dao.domain.SysRole;
import org.saas.dao.domain.SysUser;
import org.saas.dao.domain.SysUserExample;

import java.util.List;
import java.util.Set;

public interface SysUserMapper extends Mapper<SysUser, SysUserExample, Long> {

    Set<String> selectRoleByUserName(String name);

    Set<String> selectPremByUserName(String name);

    List<SysRole> selectRoleByUserId(Long id);

    int deleteUserRoleByUserId(Long id);

    int insertUserRole(List<RelUserRole> list);
}