package com.otn.service.impl;

import com.otn.dao.SysSyncLogDao;
import com.otn.pojo.SysSyncLogDTO;
import com.otn.service.SysSyncLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("sysSyncLogService")
public class SysSyncLogServiceImpl implements SysSyncLogService {
    @Resource
    private SysSyncLogDao sysSyncLogDao;

    @Override
    public List<SysSyncLogDTO> listLog() {
        return null;
    }

    @Override
    public List<SysSyncLogDTO> createLog(String operatorName) {
        return null;
    }
}
