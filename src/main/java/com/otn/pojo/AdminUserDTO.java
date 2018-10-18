package com.otn.pojo;

/**
 * Created by 韩宪斌 on 2017/7/10.
 */
public class AdminUserDTO extends BaseUserDTO {

    private String password;

    public AdminUserDTO(Long userId, String userName, String password, String userRole, String userGroup) {
        super(userId, userName, userRole, userGroup);
        this.password = password;
    }

    public AdminUserDTO() {
        super();
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
