package com.otn.controller;

import com.google.gson.Gson;
import com.otn.controller.util.TKClient;
import com.otn.pojo.AmplifierCreateInfo;
import com.otn.pojo.AmplifierDTO;
import com.otn.pojo.MaintenanceRecordDTO;
import com.otn.service.AmplifierService;
import com.otn.service.ResMaintenanceRecordService;
import com.otn.util.exception.controller.input.NullArgumentException;
import com.otn.util.exception.controller.result.NoneRemoveException;
import com.otn.util.tools.SerializableHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: 李景然
 * @Date: 2018/5/25 16:52
 * @Description:
 */
@RestController
@Api(tags = "Client", description = "发送方相关操作")
@RequestMapping("/client")
public class ClientController {
    @Resource
    private ResMaintenanceRecordService maintenanceRecordService;

    @ApiOperation(value = "测试")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Boolean test() {
        try {
            //一共451条数据
            int buchang = 44;
            List<MaintenanceRecordDTO> list = maintenanceRecordService.listRecord();
            TKClient tkClient = new TKClient();
            Gson gson = new Gson();
            String str = gson.toJson(list);
            tkClient.sendMessage(str);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
