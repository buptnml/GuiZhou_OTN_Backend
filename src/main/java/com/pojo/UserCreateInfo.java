package com.pojo;

/**
 * Created by 韩宪斌 on 2017/7/12.
 * 用户请求V2，除ID外全都有
 */
public class UserCreateInfo {
    private String userName;
    private String password;
    private String userRole;
    private String userGroup;

    public UserCreateInfo(String userName, String password, String userRole, String userGroup) {
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
        this.userGroup = userGroup;
    }

    public UserCreateInfo() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}
