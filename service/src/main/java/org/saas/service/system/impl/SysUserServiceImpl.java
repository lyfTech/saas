package org.saas.service.system.impl;

import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.saas.common.enums.ConsantEnums;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.mybatis.PageRequest;
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
import javax.security.auth.Subject;
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


    public SingleResponseHandleT<SysUser> getUserById(Long userId) {
        SingleResponseHandleT<SysUser> handleT = new SingleResponseHandleT<SysUser>();
        if (userId != null && userId != 0) {
            SysUser user = userMapper.selectByPrimaryKey(userId);
            handleT.setResult(user);
        }
        return handleT;
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

    public Page<SysUser> getAllUserInfo(SysUserExample example, PageRequest pageRequest) {
        List<SysUser> users = userMapper.selectByExampleWithRowbounds(example, pageRequest.getRowBounds());
        int i = userMapper.countByExample(example);
        Page<SysUser> page = new Page<SysUser>(users, pageRequest, i);
        return page;
    }

    public Set<String> getRolesByName(String userName) {
        return userMapper.selectRoleByUserName(userName);
    }

    public Set<String> getPremsByName(String userName) {
        return userMapper.selectPremByUserName(userName);
    }

    /**
     * 添加用户
     *
     * @param sysUser
     * @return
     */
    public BaseResponseHandle addUser(SysUser sysUser) {
        BaseResponseHandle handle = new BaseResponseHandle();
        try {
            SysUser user = getUserByName(sysUser.getUserName());
            if (user != null) {
                handle.setErrorMessage("用户已经存在");
            }
            passwordHelper.encryptPassword(sysUser);
            sysUser.setIsDelete(0);
            sysUser.setCreateTime(Calendar.getInstance().getTime());
            sysUser.setCreator(currentUserInfo().getId());
            sysUser.setModifyTime(Calendar.getInstance().getTime());
            sysUser.setModifier(currentUserInfo().getId());
            int i = userMapper.insert(sysUser);
            if (i > 0) {
                handle.setMessage("用户添加成功");
                logger.info("用户{}添加成功：", sysUser.getUserName());
            }
        } catch (Exception e) {
            handle.setErrorMessage("用户添加失败");
            logger.error("用户添加失败，失败信息：" + e.getMessage());
        }
        return handle;
    }

    public BaseResponseHandle changeState(Long id) {
        BaseResponseHandle handle = new BaseResponseHandle();
        SysUser user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            user.setStatus(user.getStatus() == 1 ? 0 : 1);
            user.setModifier(currentUserInfo().getId());
            user.setModifyTime(Calendar.getInstance().getTime());
            int i = userMapper.updateByPrimaryKey(user);
            if (i == 0) {
                handle.setErrorMessage("用户修改失败");
            }
        } else {
            handle.setErrorMessage("用户不存在");
        }
        return handle;
    }

    public SysUser currentUserInfo() {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isNoneBlank(userName)) {
            org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();
            Session session = currentUser.getSession();
            return (SysUser) session.getAttribute(ConsantEnums.CURRENT_USERINFO.getKey());
        }
        return null;
    }

}
