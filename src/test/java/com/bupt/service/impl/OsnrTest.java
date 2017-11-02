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
        String routeString = "905_两所屯变-917_信息中心123123123-901_中调";
        try {
            osnrCalculator.calculate(inputs, null, routeString, 100000000252L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("***************************************************************************");
        System.out.println(osnrCalculator.getInputPowersString());
        System.out.println(osnrCalculator.getOutputPowerString());
        System.out.println("***************************************************************************");
    }
}
