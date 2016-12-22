package org.saas.service.system.impl;

import org.apache.ibatis.session.RowBounds;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.saas.common.dto.KeyValueDto;
import org.saas.common.enums.ConsantEnums;
import org.saas.common.handle.ResponseHandle;
import org.saas.common.handle.ResponseHandleT;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.mybatis.PageRequest;
import org.saas.common.utils.StringUtils;
import org.saas.dao.domain.RelUserRole;
import org.saas.dao.domain.SysRole;
import org.saas.dao.domain.SysUser;
import org.saas.dao.domain.SysUserExample;
import org.saas.dao.mapper.SysUserMapper;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.security.auth.Subject;
import java.util.*;

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

    public BaseResponseHandle updateUser(SysUser user) {
        BaseResponseHandle handle = new BaseResponseHandle();
        try {
            user.setModifyTime(Calendar.getInstance().getTime());
            user.setModifier(currentUserInfo().getId());
            int i = userMapper.updateByPrimaryKey(user);
            if (i < 1) {
                handle.setErrorMessage("用户更新失败");
            }
        } catch (Exception e) {
            handle.setErrorMessage("用户更新异常");
            logger.error("用户更新异常：{}", e.getMessage());
        }
        return handle;
    }

    public BaseResponseHandle resetPassword(Long id, String password) {
        BaseResponseHandle handle = new BaseResponseHandle();
        SysUser user = userMapper.selectByPrimaryKey(id);
        if (user != null) {
            user.setPassword(password);
            passwordHelper.encryptPassword(user);
            int i = userMapper.updateByPrimaryKey(user);
            if (i < 1) {
                handle.setErrorMessage("重置失败");
                logger.error("用户id={}重置密码失败", user.getId());
            } else {
                logger.info("用户id={}重置密码成功", user.getId());
            }
        }
        return handle;
    }

    public ResponseHandleT<SysRole> getUserRoleByUserId(Long id) {
        ResponseHandleT<SysRole> handleT = new ResponseHandleT<SysRole>();
        if (id == null) {
            handleT.setErrorMessage("参数异常");
            return handleT;
        }
        List<SysRole> map = userMapper.selectRoleByUserId(id);
        handleT.setResult(map);
        return handleT;
    }

    @Transactional
    public BaseResponseHandle saveUserRole(Long userId, Integer[] roleIds) {
        BaseResponseHandle handle = new BaseResponseHandle();
        if (userId == null) {
            handle.setErrorMessage("参数异常");
            return handle;
        }
        try {
            int i = userMapper.deleteUserRoleByUserId(userId);
            List<RelUserRole> list = new ArrayList<RelUserRole>();
            for (Integer roleId : roleIds) {
                RelUserRole ur = new RelUserRole();
                ur.setUserId(userId);
                ur.setRoleId(Long.valueOf(roleId));
                list.add(ur);
            }
            int j = userMapper.insertUserRole(list);
            logger.info("用户[id={}]角色分配成功：移除{}—>新增{}", userId, i, j);
        } catch (Exception e) {
            logger.error("用户[id={}]角色分配异常，异常原因：{}", userId, e.getMessage());
            handle.setErrorMessage("用户分配角色失败");
        }
        return handle;
    }

}
