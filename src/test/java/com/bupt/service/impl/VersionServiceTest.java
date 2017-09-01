package com.bupt.service.impl;

import com.bupt.facade.VersionService;
import com.bupt.pojo.VersionCreateInfo;
import com.bupt.pojo.VersionSetting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * VersionService测试
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@WebAppConfiguration
public class VersionServiceTest {
    @Resource
    VersionService versionService;
    @Test
    public void saveVersionTest() throws Exception{
        VersionSetting versionSetting = new VersionSetting();
        VersionCreateInfo versionCreateInfo = new VersionCreateInfo();
        versionCreateInfo.setVersionName("测试版本123123");
        versionCreateInfo.setBaseVersionName("测试版本0");
        versionCreateInfo.setVersionSetting(versionSetting);
        versionService.saveVersion(versionCreateInfo);
    }



}
