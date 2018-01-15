package com.pojo;

/**
 * Created by 韩宪斌 on 2017/7/10.
 */
public class UserQuery {
    private String userName;
    private String password;


    public UserQuery(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public UserQuery() {
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
}
