package org.saas.service.system.impl;

import org.saas.common.enums.PermTypeEnums;
import org.saas.common.handle.BaseResponseHandle;
import org.saas.common.handle.ResponseHandleT;
import org.saas.common.handle.SingleResponseHandleT;
import org.saas.common.mybatis.Page;
import org.saas.common.mybatis.PageRequest;
import org.saas.common.utils.StringUtils;
import org.saas.dao.domain.SysPerm;
import org.saas.dao.domain.SysPermExample;
import org.saas.dao.mapper.SysPermMapper;
import org.saas.service.system.SysPermService;
import org.saas.service.system.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Created by gls on 2016/12/15.
 */
@Service
public class SysPermServiceImpl implements SysPermService {
    public static final Logger logger = LoggerFactory.getLogger(SysPermServiceImpl.class);

    @Resource
    private SysPermMapper permMapper;
    @Resource
    private SysUserService userService;
    
    public List<SysPerm> getUserPerm(String username) {
        List<SysPerm> permList = permMapper.selectUserPremByUserName(username);
        TreeUtil treeUtil = new TreeUtil(permList);
        List<SysPerm> perms = treeUtil.generateTreeNode(new SysPerm(),1L);
        return perms;
    }

    public Set<String> getPremByRoleId(Long roleId){
        Set<String> perms = permMapper.selectRolePremByRoleId(roleId);
        return perms;
    }

    public Page<SysPerm> queryPermPage(SysPermExample example, PageRequest pageRequest) {
        List<SysPerm> roles = permMapper.selectByExampleWithRowbounds(example, pageRequest.getRowBounds());
        int i = permMapper.countByExample(example);
        Page<SysPerm> page = new Page<SysPerm>(roles, pageRequest, i);
        return page;
    }

    public SingleResponseHandleT<SysPerm> getPermById(Long permId) {
        SingleResponseHandleT<SysPerm> handleT = new SingleResponseHandleT<SysPerm>();
        if (permId != null && permId != 0) {
            SysPerm role = permMapper.selectByPrimaryKey(permId);
            handleT.setResult(role);
        }
        return handleT;
    }

    public SysPerm getPermByCode(String code) {
        if (StringUtils.isBlank(code)) return null;
        SysPermExample example = new SysPermExample();
        example.createCriteria().andCodeEqualTo(code);
        List<SysPerm> perms = permMapper.selectByExample(example);
        if (perms != null && perms.size() > 0) {
            return perms.get(0);
        }
        return null;
    }

    public BaseResponseHandle changePermState(Long id) {
        BaseResponseHandle handle = new BaseResponseHandle();
        SysPerm perm = permMapper.selectByPrimaryKey(id);
        if (perm != null) {
            perm.setStatus(perm.getStatus() == 1 ? 0 : 1);
            perm.setModifier(userService.currentUserInfo().getId().toString());
            perm.setModifyTime(Calendar.getInstance().getTime());
            int i = permMapper.updateByPrimaryKey(perm);
            if (i == 0) {
                handle.setErrorMessage("权限修改失败");
            }
        } else {
            handle.setErrorMessage("权限不存在");
        }
        return handle;
    }

    public BaseResponseHandle addPerm(SysPerm perm) {
        BaseResponseHandle handle = new BaseResponseHandle();
        try {
            SysPerm roleByName = getPermByCode(perm.getCode());
            if (roleByName != null) {
                handle.setErrorMessage("权限已经存在");
                return handle;
            }
            perm.setCreateTime(Calendar.getInstance().getTime());
            perm.setCreator(userService.currentUserInfo().getId().toString());
            perm.setModifyTime(Calendar.getInstance().getTime());
            perm.setModifier(userService.currentUserInfo().getId().toString());
            int i = permMapper.insert(perm);
            if (i > 0) {
                handle.setMessage("权限添加成功");
                logger.info("权限{}[{}]添加成功：", perm.getName(), perm.getCode());
            }
        } catch (Exception e) {
            handle.setErrorMessage("权限添加失败");
            logger.error("权限添加异常，失败信息：" + e.getMessage());
        }
        return handle;
    }

    public BaseResponseHandle updateRole(SysPerm perm) {
        BaseResponseHandle handle = new BaseResponseHandle();
        try {
            perm.setModifyTime(Calendar.getInstance().getTime());
            perm.setModifier(userService.currentUserInfo().getId().toString());
            int i = permMapper.updateByPrimaryKey(perm);
            if (i < 1) {
                handle.setErrorMessage("权限更新失败");
            }
        } catch (Exception e) {
            handle.setErrorMessage("权限更新异常");
            logger.error("权限更新异常", e.getMessage());
        }
        return handle;
    }

    public ResponseHandleT<SysPerm> getAllMenu(){
        ResponseHandleT<SysPerm> handleT = new ResponseHandleT<SysPerm>();
        SysPermExample example = new SysPermExample();
        example.createCriteria().andTypeEqualTo(PermTypeEnums.MENU.getKey());
        example.setOrderByClause("sort asc");
        List<SysPerm> perms = permMapper.selectByExample(example);
        handleT.setResult(perms);
        return handleT;
    }
}
