package com.otn.controller;

import com.otn.controller.util.TKServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 李景然
 * @Date: 2018/5/25 21:31
 * @Description:
 */
@RestController
@Api(tags = "Server", description = "接收方相关操作")
@RequestMapping("/server")
public class ServerController {
    @ApiOperation(value = "测试")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Boolean test() {
        try {
            TKServer tkServer=TKServer.getTkServer();
            tkServer.listenClient();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
