package com.otn.service.impl;

import com.otn.dao.ResMaintenanceRecordDao;
import com.otn.entity.ResMaintenanceRecord;
import com.otn.pojo.MaintenanceRecordDTO;
import com.otn.pojo.MaintenanceRecordQuery;
import com.otn.service.ResMaintenanceRecordService;
import com.otn.util.exception.controller.result.NoneRemoveException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    ResMaintenanceRecord createDO(MaintenanceRecordQuery recordDTO) {
        ResMaintenanceRecord result = new ResMaintenanceRecord();
        BeanUtils.copyProperties(recordDTO, result);
        result.setMaintenanceRecordId(recordDTO.getId());
        result.setMaintenanceRecordSubId(recordDTO.getIdNo());
        return result;
    }


    @Override
    public MaintenanceRecordDTO addRecord(MaintenanceRecordQuery record) {
        try {
            recordDao.insertSelective(createDO(record));
        } catch (DuplicateKeyException e) {
            recordDao.updateByPrimaryKeySelective(createDO(record));
        }
        return createDTO(recordDao.selectByPrimaryKey(createDO(record)));
    }

    @Override
    public List<MaintenanceRecordDTO> listRecord() {
        return recordDao.selectAll().stream().sorted(Comparator.comparing(ResMaintenanceRecord::getGmtModified)
                .reversed()).map(this::createDTO).collect(Collectors.toList());
    }

    @Override
    public MaintenanceRecordDTO updateRecord(Long maintenanceRecordId) {
        ResMaintenanceRecord record = recordDao.selectByPrimaryKey(maintenanceRecordId);
        record.setIsDone("1");
        recordDao.updateByPrimaryKeySelective(record);
        return createDTO(recordDao.selectByPrimaryKey(record));
    }

    @Override
    public boolean deleteByMaintenanceRecordId( List<Long> maintenanceRecordIds){
        if (maintenanceRecordIds.size() == 0)
            return true;
        for (Long maintenanceRecordId : maintenanceRecordIds) {
            if (recordDao.deleteByPrimaryKey(maintenanceRecordId) == 0) {
                throw new NoneRemoveException();
            }
        }
        return true;
    }
}
