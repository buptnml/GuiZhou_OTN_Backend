package com.bupt.service;

import com.bupt.pojo.UserDTO;
import com.bupt.pojo.UserQuery;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by 韩宪斌 on 2017/7/10.
 * UserService测试文件
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest {
    
    private static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);
    @Resource
    private UserService userService;
    
    @Test
    public void Test1() {
        UserDTO userDTO = new UserDTO(13L, "testName", "123test", "普通用户", "北京邮电大学");
        assert userService.saveUser(userDTO);
    }
    
    @Test
    public void Test2() {
        UserQuery userQuery = new UserQuery("testName", "123test");
        assert userService.getUser(userQuery) != null;
    }
    
    @Test
    public void Test3() {
        assert userService.listUser().size() > 0;
    }
    
    @Test
    public void Test4() {
        UserDTO userDTO = new UserDTO(13L, "testName", "123test", "管理员", "北京邮电大学测试");
        assert userService.updateUser(userDTO);
    }
    
}
