package com.bupt.service;

import com.bupt.pojo.UserRoleCreateInfo;
import com.bupt.pojo.UserRoleDTO;

import java.util.List;

/**
 * 角色Service层接口
 */
public interface UserRoleService {
    /**
     * 创建新角色信息
     * @param userRoleCreateInfo
     * @return
     */
    UserRoleDTO saveUserRole(UserRoleCreateInfo userRoleCreateInfo);

    /**
     * 批量删除角色
     * @param userRoleIdList
     */
    void listRemoveUserRole(List<Long> userRoleIdList);

    /**
     * 获取所有角色
     * @return
     */
    List<UserRoleDTO> listUserRole();
}
