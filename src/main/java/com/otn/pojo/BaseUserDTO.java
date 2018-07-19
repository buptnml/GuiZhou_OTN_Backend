package com.otn.pojo;

public class BaseUserDTO {
    private Long userId;
    private String userName;
    private String userRole;
    private String userGroup;

    public BaseUserDTO(Long userId, String userName, String userRole, String userGroup) {
        this.userId = userId;
        this.userName = userName;
        this.userRole = userRole;
        this.userGroup = userGroup;
    }

    public BaseUserDTO() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
