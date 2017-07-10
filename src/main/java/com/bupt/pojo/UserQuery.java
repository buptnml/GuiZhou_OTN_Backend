package com.bupt.pojo;

/**
 * Created by 韩宪斌 on 2017/7/10.
 */
public class UserQuery {
    private String userName;
    private String passWord;
    
    
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
    
    public UserQuery(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }
    
    public UserQuery() {
    }
}
