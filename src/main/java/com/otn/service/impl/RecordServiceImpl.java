package com.otn.service.impl;

import com.otn.dao.ResRecordDao;
import com.otn.entity.ResRecord;
import com.otn.pojo.RecordCreateQuery;
import com.otn.pojo.RecordDTO;
import com.otn.service.RecordService;
import com.otn.util.exception.controller.result.NoneSaveException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "recordService")
public class RecordServiceImpl implements RecordService {
    @Resource
    private ResRecordDao resRecordDao;

    @Override
    public List<RecordDTO> listRecords(Long versionId, String target, Date startTime, Date endTime) {
        Example example = new Example(ResRecord.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(target)) {
            criteria.andLike("target", "%" + target + "%");
        }
        criteria.andEqualTo("versionId", versionId);
        if (startTime != null) {
            criteria.andGreaterThanOrEqualTo("gmtCreate", startTime);
        }
        if (endTime != null) {
            criteria.andLessThanOrEqualTo("gmtCreate", endTime);
        }
        return resRecordDao.selectByExample(example).stream().map(this::toDTO).collect(Collectors.toList());
    }


    private ResRecord toDo(Object recordCreateQuery) {
        if (null == recordCreateQuery) {
            return null;
        }
        ResRecord record = new ResRecord();
        BeanUtils.copyProperties(recordCreateQuery, record);
        return record;
    }

    private RecordDTO toDTO(Object recordDO) {
        if (null == recordDO) {
            return null;
        }
        RecordDTO record = new RecordDTO();
        BeanUtils.copyProperties(recordDO, record);
        return record;
    }


    @Override
    public RecordDTO addRecord(Long versionId, RecordCreateQuery recordCreateQuery) {
        ResRecord resRecord = toDo(recordCreateQuery);
        resRecord.setVersionId(versionId);
        if (resRecordDao.insertSelective(resRecord) > 0) {
            return toDTO(recordCreateQuery);
        }
        throw new NoneSaveException();
    }
}
