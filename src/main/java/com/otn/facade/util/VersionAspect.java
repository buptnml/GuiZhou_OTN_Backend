package com.otn.facade.util;

import com.otn.facade.VersionBackUpService;
import com.otn.pojo.VersionDTO;
import com.otn.pojo.VersionDTOWithVersionDictDTO;
import com.otn.webservice.com.pojo.WebServiceConfigInfo;
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
            "(execution(* com.otn.facade.VersionConcreteService.saveVersion(..)) && args(..))) ")
    public void initBackUp(VersionDTO versionDTO) {
        versionBackUpService.initBackUp(versionDTO.getVersionId());
    }

    @Around(value = "(execution(* com.otn.facade.VersionConcreteService.getVersion(..)) && args(versionId,..))) ",
            argNames = "point,versionId")
    public VersionDTOWithVersionDictDTO restore(ProceedingJoinPoint point, Long versionId) throws Throwable {
        //基础版本不需要恢复或者保存操作
        if (versionId != webServiceConfigInfo.getBASIC_VERSION_ID()) versionBackUpService.restoreBackUp(versionId);
        return (VersionDTOWithVersionDictDTO) point.proceed();
    }


}
