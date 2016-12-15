package org.saas.dao.mapper;

import org.saas.common.mybatis.Mapper;
import org.saas.dao.domain.SysPerm;
import org.saas.dao.domain.SysPermExample;

import java.util.List;

public interface SysPermMapper extends Mapper<SysPerm, SysPermExample, Long> {

    List<SysPerm> selectUserPremByUserName(String name);

}