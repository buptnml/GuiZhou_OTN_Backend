package com.bupt.service.impl;


import com.bupt.pojo.OsnrChannelCreateInfo;
import com.bupt.pojo.ChannelQuery;
import com.bupt.service.OsnrChannelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@WebAppConfiguration
public class OsnrChannelServiceTest {
    @Resource
    OsnrChannelService osnrChannelService;

//    @Test
//    public void saveOsnrChannel() throws Exception {
//        OsnrChannelCreateInfo osnrChannelCreateInfo = new OsnrChannelCreateInfo();
//        osnrChannelCreateInfo.setChannelFrequency("测试速率");
//        osnrChannelCreateInfo.setChannelRate("测试频率");
//        osnrChannelService.saveOsnrChannel(new ChannelQuery(100000000010L, 02L, false),"测试路由", osnrChannelCreateInfo);
//    }


    @Test
    public void batchCreateTest() throws Exception {
        osnrChannelService.batchCreate(100000000010L,223L);
    }

    @Test
    public void batchRemoveTest() throws Exception {
        osnrChannelService.batchRemove(223L);
    }
//
//    @Test
//    public void updateOsnrChannelTest(){
//        OsnrChannelDTO osnrChannelCreateInfo = new OsnrChannelDTO();
//        osnrChannelCreateInfo.setChannelFrequency("测试速率123");
//        osnrChannelCreateInfo.setChannelRate("测试频率123");
//        osnrChannelCreateInfo.setChannelName("测试波道123");
//        osnrChannelCreateInfo.setChannelRoute("测试路由123");
//        osnrChannelService.updateOsnrChannel(100000000010L,4L,osnrChannelCreateInfo);
//    }
}
