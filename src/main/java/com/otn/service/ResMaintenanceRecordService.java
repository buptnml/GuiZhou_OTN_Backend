package com.otn.service;

import com.otn.pojo.MaintenanceRecordDTO;
import com.otn.pojo.MaintenanceRecordQuery;

import java.util.List;


public interface ResMaintenanceRecordService {
    /**
     * 添加一条记录
     *
     * @param record
     * @return
     */
    MaintenanceRecordDTO addRecord(MaintenanceRecordQuery record);

    /**
     * 获取所有记录
     *
     * @return
     */
    List<MaintenanceRecordDTO> listRecord();

    /**
     * 更新一条记录为已读
     *
     * @param maintenanceRecordId
     * @return
     */
    MaintenanceRecordDTO updateRecord(Long maintenanceRecordId);

}
