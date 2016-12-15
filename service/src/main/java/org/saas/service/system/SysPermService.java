package org.saas.service.system;

import org.saas.dao.domain.SysPerm;

import java.util.List;

public interface SysPermService {
    List<SysPerm> getUserPerm(String username);
}
