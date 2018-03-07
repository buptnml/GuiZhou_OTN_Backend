package com.otn.service.impl;

import com.otn.facade.VersionConcreteService;
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
    VersionConcreteService versionConcreteService;

    @Test
    public void nothing() {
        long startTime = System.currentTimeMillis();
//        versionBackUpService.initBackUp(100000000309L);
//        versionBackUpService.saveBackUp(100000000309L);
        versionConcreteService.dataSynchronize(100000000373L, 100000000373L);

        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
    }


}
