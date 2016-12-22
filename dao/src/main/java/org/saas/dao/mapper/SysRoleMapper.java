package org.saas.dao.mapper;

import org.saas.common.mybatis.Mapper;
import org.saas.dao.domain.RelRolePerm;
import org.saas.dao.domain.SysRole;
import org.saas.dao.domain.SysRoleExample;

import java.util.List;

public interface SysRoleMapper extends Mapper<SysRole, SysRoleExample, Long> {
    int deleteRolePermByRoleId(Long id);

    int insertRolePerm(List<RelRolePerm> list);
}