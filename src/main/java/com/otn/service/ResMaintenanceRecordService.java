package com.otn.service;

import com.otn.entity.ResMaintenanceRecord;
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

    ResMaintenanceRecord latestRecord();

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

    /**
     * 批量删除检修单, 根据 检修单ID
     *
     * @param maintenanceRecordIds
     */
    boolean deleteByMaintenanceRecordId( List<Long> maintenanceRecordIds);

}
