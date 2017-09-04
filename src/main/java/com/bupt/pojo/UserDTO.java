package com.bupt.pojo;

/**
 * Created by 韩宪斌 on 2017/7/10.
 */
public class UserDTO {
    private Long userId;
    private String userName;
    private String password;
    private String userRole;
    private String userGroup;

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
    
    public UserDTO(Long userId, String userName, String password, String userRole, String userGroup) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
        this.userGroup = userGroup;
    }
    
    public UserDTO() {
    }
    
    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userGroup='" + userGroup + '\'' +
                '}';
    }
}
