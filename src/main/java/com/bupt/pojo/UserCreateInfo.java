package com.bupt.pojo;

/**
 * Created by 韩宪斌 on 2017/7/12.
 * 用户请求V2，除ID外全都有
 */
public class UserCreateInfo {
    private String userName;
    private String passWord;
    private String userRole;
    private String userGroup;
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassWord() {
        return passWord;
    }
    
    public void setPassWord(String passWord) {
        this.passWord = passWord;
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
    
    public UserCreateInfo(String userName, String passWord, String userRole, String userGroup) {
        this.userName = userName;
        this.passWord = passWord;
        this.userRole = userRole;
        this.userGroup = userGroup;
    }
    
    public UserCreateInfo() {
    }
}
