package org.saas.dao.mapper;

import org.saas.common.mybatis.Mapper;
import org.saas.dao.domain.SysPerm;
import org.saas.dao.domain.SysPermExample;

import java.util.List;
import java.util.Set;

public interface SysPermMapper extends Mapper<SysPerm, SysPermExample, Long> {

    List<SysPerm> selectUserPremByUserName(String name);

    Set<String> selectRolePremByRoleId(Long id);
}