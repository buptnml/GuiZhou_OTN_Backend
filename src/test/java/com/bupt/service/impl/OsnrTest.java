package com.bupt.service.impl;

import com.bupt.facade.OSNRCalculator.OSNRCalculator;
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
        inputs[0][0] = -10;
        String routeString = "703_贵阳变-708_信息中心-705_中调";
        osnrCalculator.calculate(inputs, null, routeString, 100000000000L);
        System.out.println(osnrCalculator.getInputPowersString());
        System.out.println(osnrCalculator.getOutputPowerString());
    }
}
