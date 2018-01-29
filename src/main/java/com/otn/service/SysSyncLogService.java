package com.otn.service;


import com.otn.pojo.SysSyncLogDTO;

import java.util.List;

public interface SysSyncLogService {
    List<SysSyncLogDTO> listLog();

    List<SysSyncLogDTO> createLog(String operatorName);
}
