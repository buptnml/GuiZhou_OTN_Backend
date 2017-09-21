package com.bupt.service.impl;

import com.bupt.pojo.UserCreateInfo;
import com.bupt.pojo.UserRoleCreateInfo;
import com.bupt.service.UserRoleService;
import io.swagger.annotations.Example;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@WebAppConfiguration
public class UserRoleServiceTest {
    @Resource
    UserRoleService userRoleService;
//    @Test
//    public void saveUserRoleTest() throws Exception{
//        UserRoleCreateInfo testCreate = new UserRoleCreateInfo();
//        testCreate.setRoleName(String.valueOf(Calendar.SECOND));
//        userRoleService.saveUserRole(testCreate);
//    }

//    @Test
//    public void listRemoveUserRoleTest() throws Exception{
//        List<Long> testList = new ArrayList<>();
//        testList.add(3L);
//        testList.add(5L);
//        userRoleService.listRemoveUserRole(testList);
//    }


}
