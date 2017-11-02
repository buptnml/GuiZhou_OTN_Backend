package com.bupt.service.impl;

import com.bupt.pojo.NXAnalyseItemDTO;
import com.bupt.service.NXAnalyseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xf on 2017/10/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@WebAppConfiguration
public class NXAnalyseServiceTest {
    @Resource
    private NXAnalyseService nxAnalyseService;

    @Test
    public void equipAnaTest() {

        List<NXAnalyseItemDTO> list =
                nxAnalyseService.analyseEquip(100000000000L, 1);
        NXAnalyseItemDTO nxAna = list.get(1);
        System.out.println("------------------------------" + list.size() + nxAna.getItemName() + "---");
    }
}
