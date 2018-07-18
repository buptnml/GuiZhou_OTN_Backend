package com.otn.controller;

import com.otn.pojo.UploadFileInfo;
import com.otn.util.FileHelper;
import com.otn.webservice.WebServiceFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@Api(tags = "utils", description = "工具类接口")
@RequestMapping("/utils")
public class UtilController {
    @Resource
    private WebServiceFactory webServiceFactory;


    @ApiOperation(value = "增加", notes = "添加新链路类型")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean createLinkType(@RequestBody UploadFileInfo uploadFileInfo) {
        String path = FileHelper.saveUrlAs(uploadFileInfo.getUrl());
        String data = "";
        try {
            data = FileHelper.encodeBase64File(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = webServiceFactory.uploadFile(FileHelper.createXMLStreamForUpload(uploadFileInfo
                .getFileName(), uploadFileInfo.getFileType(), data));
        return response != null;
    }

}
