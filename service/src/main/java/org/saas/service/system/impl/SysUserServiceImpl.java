package org.saas.service.system.impl;

import org.apache.ibatis.session.RowBounds;
import org.saas.common.BaseResponseHandle;
import org.saas.common.dto.KeyValueDto;
import org.saas.common.utils.EndecryptUtils;
import org.saas.common.utils.StringUtils;
import org.saas.dao.domain.SysUser;
import org.saas.dao.domain.SysUserExample;
import org.saas.dao.mapper.SysUserMapper;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Service
public class SysUserServiceImpl implements SysUserService {
    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserMapper userMapper;
    @Autowired
    private PasswordHelper passwordHelper;


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

    public Set<String> getRolesByName(String userName) {
        return userMapper.selectRoleByUserName(userName);
    }

    public Set<String> getPremsByName(String userName) {
        return userMapper.selectPremByUserName(userName);
    }

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    public BaseResponseHandle addUser(SysUser sysUser) {
        BaseResponseHandle handle = new BaseResponseHandle();
        try {
            SysUser user = getUserByName(sysUser.getUserName());
            if (user != null){
                handle.setErrorMessage("用户已经存在");
            }
            passwordHelper.encryptPassword(sysUser);
            sysUser.setStatus(0);
            sysUser.setIsDelete(0);
            sysUser.setCreateTime(Calendar.getInstance().getTime());
            sysUser.setCreator(1L);// TODO: 2016/12/13 将来要改成当前登录用户的id
            sysUser.setModifyTime(Calendar.getInstance().getTime());
            sysUser.setModifier(1L);// TODO: 2016/12/13 将来要改成当前登录用户的id
            int i = userMapper.insert(sysUser);
            if (i > 0){
                handle.setMessage("用户添加成功");
                logger.info("用户{}添加成功：", sysUser.getUserName());
            }
        } catch (Exception e) {
            handle.setErrorMessage("用户添加失败");
            logger.error("用户添加失败，失败信息："+e.getMessage());
        }
        return handle;
    }
}
