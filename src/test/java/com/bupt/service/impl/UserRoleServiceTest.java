package com.bupt.service.impl;

import com.bupt.service.UserRoleService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

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
