package org.saas.service.system.impl;

import org.saas.dao.domain.SysPerm;
import org.saas.dao.mapper.SysPermMapper;
import org.saas.service.system.SysPermService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gls on 2016/12/15.
 */
@Service
public class SysPermServiceImpl implements SysPermService {
    public static final Logger logger = LoggerFactory.getLogger(SysPermServiceImpl.class);

    @Resource
    private SysPermMapper permMapper;


    public List<SysPerm> getUserPerm(String username) {
        List<SysPerm> permList = permMapper.selectUserPremByUserName(username);
        TreeUtil treeUtil = new TreeUtil(permList);
        List<SysPerm> perms = treeUtil.generateTreeNode(new SysPerm(),0L);
        return perms;
    }
}
