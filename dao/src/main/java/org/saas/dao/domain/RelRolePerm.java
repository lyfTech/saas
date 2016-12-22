package org.saas.dao.domain;

import java.io.Serializable;

/**
 * Created by gls on 2016/12/21.
 */
public class RelRolePerm implements Serializable{

    private Long roleId;
    private Long permId;

    public RelRolePerm() {
    }

    public RelRolePerm(Long roleId, Long permId) {
        this.roleId = roleId;
        this.permId = permId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermId() {
        return permId;
    }

    public void setPermId(Long permId) {
        this.permId = permId;
    }

    @Override
    public String toString() {
        return "RelRolePerm{" +
                "roleId=" + roleId +
                ", permId=" + permId +
                '}';
    }
}
