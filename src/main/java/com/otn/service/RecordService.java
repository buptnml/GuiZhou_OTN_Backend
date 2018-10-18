package com.otn.service;

import com.otn.pojo.RecordCreateQuery;
import com.otn.pojo.RecordDTO;

import java.util.Date;
import java.util.List;

public interface RecordService {
    List<RecordDTO> listRecords(Long versionId, String target, Date startTime, Date endTime);

    RecordDTO addRecord(Long versionId, RecordCreateQuery recordCreateQuery);

    String getRecord(Long id,String type);
}
