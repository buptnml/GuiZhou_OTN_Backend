package com.bupt.pojo;

import org.springframework.lang.Nullable;

public class UserRoleCreateInfo {
    private String roleName;
    @Nullable
    private String roleDescription;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Nullable
    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(@Nullable String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
