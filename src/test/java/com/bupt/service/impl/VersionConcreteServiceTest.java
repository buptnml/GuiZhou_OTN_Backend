package com.bupt.service.impl;

import com.bupt.facade.VersionConcreteService;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

/**
 * VersionService测试
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@WebAppConfiguration
public class VersionConcreteServiceTest {
    @Resource
    VersionConcreteService versionConcreteService;
//    @Test
//    public void saveVersionTest() throws Exception{
//        VersionSetting versionSetting = new VersionSetting();
//        VersionQuery versionQuery = new VersionQuery();
//        versionQuery.setVersionName("测试版本123123");
//        versionQuery.setBaseVersionName("测试版本0");
//        versionQuery.setVersionSetting(versionSetting);
//        versionConcreteService.saveVersion(versionQuery);
//    }


}
