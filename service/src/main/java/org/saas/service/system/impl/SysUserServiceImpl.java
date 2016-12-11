package org.saas.service.system.impl;

import org.apache.ibatis.session.RowBounds;
import org.saas.common.utils.StringUtils;
import org.saas.dao.domain.SysUser;
import org.saas.dao.domain.SysUserExample;
import org.saas.dao.mapper.SysUserMapper;
import org.saas.service.system.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper userMapper;


    public SysUser getUserById(Long userId) {
        if (userId != null && userId != 0) {
            SysUser user = userMapper.selectByPrimaryKey(userId);
            return user;
        }
        return null;
    }

    public SysUser getUserByName(String userName) {
        if (StringUtils.isBlank(userName)) return null;
        SysUserExample example = new SysUserExample();
        example.createCriteria().andUserNameEqualTo(userName);
        List<SysUser> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public List<SysUser> getAllUserInfo(int offset, int limit) {
        SysUserExample example = new SysUserExample();
        RowBounds rowBounds = new RowBounds(offset, limit);
        List<SysUser> users = userMapper.selectByExampleWithRowbounds(example, rowBounds);
        if (users != null && users.size() > 0) {
            return users;
        }
        return null;
    }
}
