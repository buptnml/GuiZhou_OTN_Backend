package com.bupt.service.impl;

import com.bupt.facade.OSNRCalculator.impl.OSNRCalculator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@WebAppConfiguration
public class OsnrTest {

    @Resource
    private
    OSNRCalculator osnrCalculator;

    @Test
    public void nothing() {
        double[][] inputs = new double[1][1];
        inputs[0][0] = -30;
        String routeString = "803_凯里变-812_黄平变-811_镇远变-805_铜仁变";
        osnrCalculator.calculate(inputs, new double[3][3], routeString, 100000000000L);
        System.out.println(osnrCalculator.getResult());
    }
}
