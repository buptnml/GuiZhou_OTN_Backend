package com.bupt.facade.util;

import com.bupt.pojo.VersionDTO;
import com.bupt.pojo.VersionDTOWithVersionDictDTO;
import com.bupt.service.VersionBackUpService;
import com.bupt.webservice.com.pojo.WebServiceConfigInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 操作版本数据保存与恢复行为的切面
 */
@Component
@Aspect
public class VersionAspect {
    @Resource
    private VersionBackUpService versionBackUpService;
    @Resource
    private WebServiceConfigInfo webServiceConfigInfo;

    @AfterReturning(returning = "versionDTO", value =
            "(execution(* com.bupt.facade.VersionConcreteService.saveVersion(..)) && args(..))) ")
    public void initBackUp(VersionDTO versionDTO) {
        versionBackUpService.initBackUp(versionDTO.getVersionId());
    }

    @Around(value = "(execution(* com.bupt.facade.VersionConcreteService.getVersion(..)) && args(versionId,..))) ",
            argNames = "point,versionId")
    public VersionDTOWithVersionDictDTO restore(ProceedingJoinPoint point, Long versionId) throws Throwable {
        //基础版本不需要恢复或者保存操作
        if (versionId != webServiceConfigInfo.getBASIC_VERSION_ID()) versionBackUpService.restoreBackUp(versionId);
        return (VersionDTOWithVersionDictDTO) point.proceed();
    }


}
