package org.saas.service.system.impl;

import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.mybatis.PageRequest;
import org.saas.common.utils.StringUtils;
import org.saas.dao.domain.SysRole;
import org.saas.dao.domain.SysRoleExample;
import org.saas.dao.mapper.SysRoleMapper;
import org.saas.service.system.SysRoleService;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    public static final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);
    @Resource
    private SysRoleMapper roleMapper;
    @Resource
    private SysUserService userService;

    public Page<SysRole> queryRolePage(SysRoleExample example, PageRequest pageRequest) {
        List<SysRole> roles = roleMapper.selectByExampleWithRowbounds(example, pageRequest.getRowBounds());
        int i = roleMapper.countByExample(example);
        Page<SysRole> page = new Page<SysRole>(roles, pageRequest, i);
        return page;
    }

    public SingleResponseHandleT<SysRole> getRoleById(Long roleId) {
        SingleResponseHandleT<SysRole> handleT = new SingleResponseHandleT<SysRole>();
        if (roleId != null && roleId != 0) {
            SysRole role = roleMapper.selectByPrimaryKey(roleId);
            handleT.setResult(role);
        }
        return handleT;
    }

    public SysRole getRoleByName(String roleName) {
        if (StringUtils.isBlank(roleName)) return null;
        SysRoleExample example = new SysRoleExample();
        example.createCriteria().andNameEqualTo(roleName);
        List<SysRole> roles = roleMapper.selectByExample(example);
        if (roles != null && roles.size() > 0) {
            return roles.get(0);
        }
        return null;
    }

    public BaseResponseHandle changeRoleState(Long id) {
        BaseResponseHandle handle = new BaseResponseHandle();
        SysRole role = roleMapper.selectByPrimaryKey(id);
        if (role != null) {
            role.setStatus(role.getStatus() == 1 ? 0 : 1);
            role.setModifier(userService.currentUserInfo().getId());
            role.setModifyTime(Calendar.getInstance().getTime());
            int i = roleMapper.updateByPrimaryKey(role);
            if (i == 0) {
                handle.setErrorMessage("角色修改失败");
            }
        } else {
            handle.setErrorMessage("角色不存在");
        }
        return handle;
    }

    public BaseResponseHandle addRole(SysRole role) {
        BaseResponseHandle handle = new BaseResponseHandle();
        try {
            SysRole roleByName = getRoleByName(role.getName());
            if (roleByName != null) {
                handle.setErrorMessage("角色已经存在");
                return handle;
            }
            role.setIsDel(0);
            role.setCreateTime(Calendar.getInstance().getTime());
            role.setCreator(userService.currentUserInfo().getId());
            role.setModifyTime(Calendar.getInstance().getTime());
            role.setModifier(userService.currentUserInfo().getId());
            int i = roleMapper.insert(role);
            if (i > 0) {
                handle.setMessage("角色添加成功");
                logger.info("角色{}添加成功：", role.getName());
            }
        } catch (Exception e) {
            handle.setErrorMessage("角色添加失败");
            logger.error("角色添加异常，失败信息：" + e.getMessage());
        }
        return handle;
    }

    public BaseResponseHandle updateRole(SysRole role) {
        BaseResponseHandle handle = new BaseResponseHandle();
        try {
            role.setModifyTime(Calendar.getInstance().getTime());
            role.setModifier(userService.currentUserInfo().getId());
            int i = roleMapper.updateByPrimaryKey(role);
            if (i < 1) {
                handle.setErrorMessage("角色更新失败");
            }
        } catch (Exception e) {
            handle.setErrorMessage("角色更新异常");
            logger.error("角色更新异常", e.getMessage());
        }
        return handle;
    }

}
