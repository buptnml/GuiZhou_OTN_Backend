package com.otn.service.impl;

import com.otn.dao.ResMaintenanceRecordDao;
import com.otn.entity.ResMaintenanceRecord;
import com.otn.pojo.MaintenanceRecordDTO;
import com.otn.service.ResMaintenanceRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("MaintenanceRecordService")
public class ResMaintenanceRecordServiceImpl implements ResMaintenanceRecordService {

    @Resource
    private ResMaintenanceRecordDao recordDao;


    MaintenanceRecordDTO createDTO(ResMaintenanceRecord record) {
        MaintenanceRecordDTO result = new MaintenanceRecordDTO();
        BeanUtils.copyProperties(record, result);
        result.setId(record.getMaintenanceRecordId());
        result.setIdNo(record.getMaintenanceRecordSubId());
        return result;
    }

    ResMaintenanceRecord createDO(MaintenanceRecordDTO recordDTO) {
        ResMaintenanceRecord result = new ResMaintenanceRecord();
        BeanUtils.copyProperties(recordDTO, result);
        result.setMaintenanceRecordId(recordDTO.getId());
        result.setMaintenanceRecordSubId(recordDTO.getIdNo());
        return result;
    }

    @Override
    public MaintenanceRecordDTO addRecord(MaintenanceRecordDTO record) {
        recordDao.insertSelective(createDO(record));
        return createDTO(recordDao.selectByPrimaryKey(createDO(record)));
    }
}
