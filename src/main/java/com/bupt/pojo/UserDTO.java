package com.bupt.pojo;

/**
 * Created by 韩宪斌 on 2017/7/10.
 */
public class UserDTO {
    private Long id;
    private String userName;
    private String passWord;
    private String userRole;
    private String userGroup;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public UserDTO(Long id, String userName, String passWord, String userRole, String userGroup) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.userRole = userRole;
        this.userGroup = userGroup;
    }
    
    public UserDTO() {
    }
    
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userGroup='" + userGroup + '\'' +
                '}';
    }
}
