package org.saas.dao.domain;

import java.io.Serializable;

/**
 * Created by gls on 2016/12/21.
 */
public class RelUserRole implements Serializable{

    private Long userId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "RelUserRole{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
