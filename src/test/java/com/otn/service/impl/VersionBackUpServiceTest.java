package com.otn.service.impl;

import com.otn.service.VersionBackUpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@WebAppConfiguration
public class VersionBackUpServiceTest {
    @Resource
    private
    VersionBackUpService versionBackUpService;

    @Test
    public void nothing() {
        long startTime = System.currentTimeMillis();
//        versionBackUpService.initBackUp(100000000309L);
//        versionBackUpService.saveBackUp(100000000309L);
        versionBackUpService.restoreBackUp(100000000309L);

        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
    }


}
