package com.otn.service.impl;

import com.csvreader.CsvWriter;
import com.otn.dao.ResMaintenanceRecordDao;
import com.otn.entity.ResMaintenanceRecord;
import com.otn.pojo.MaintenanceRecordDTO;
import com.otn.pojo.MaintenanceRecordQuery;
import com.otn.service.ResMaintenanceRecordService;
import com.otn.util.exception.controller.result.NoneRemoveException;
import com.otn.util.exception.controller.result.RequestResultErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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
        result.setGmtCreate(new Date());
        result.setGmtModified(new Date());
        return result;
    }


    @Override
    public MaintenanceRecordDTO addRecord(MaintenanceRecordQuery record) {
        try {
            recordDao.insertSelective(createDO(record));
        } catch (DuplicateKeyException e) {
            recordDao.updateByPrimaryKeySelective(createDO(record));
        }

        ResMaintenanceRecord data = recordDao.selectByPrimaryKey(createDO(record));
        List<ResMaintenanceRecord> list = new ArrayList<>();
        list.add(data);

        String csvFilePath =System.getProperty("catalina.home")+"/csv/"+data.getMaintenanceRecordId() + ".csv";

        //获取类属性，用于csv的表头
        Field[] fields = data.getClass().getDeclaredFields();
        String[] csvHeaders = new String[fields.length];
        for (short i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            csvHeaders[i] = fieldName;
        }

        writeCSV(list, csvFilePath, csvHeaders);
        return createDTO(data);
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

    public static <T> void writeCSV(Collection<T> dataSet, String csvFilePath, String[] csvHeaders)  {
        try {
        //判断文件是否存在,存在则删除,然后创建新表格
        File tmp = new File(csvFilePath);
        if (tmp.exists()) {
            if (tmp.delete()) {
                //logger.info(csvFilePath + Constant.DUPLICATE_FILE_DELETE);
            }
        }
        //定义路径，分隔符，编码
        CsvWriter csvWriter = new CsvWriter(csvFilePath, ',', Charset.forName("GBK"));

        //写表头
        csvWriter.writeRecord(csvHeaders);

        //遍历集合 写内容
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            T t = (T) it.next();
            //获取类属性
            Field[] fields = t.getClass().getDeclaredFields();
            String[] csvContent = new String[fields.length];
            for (short i = 0; i < fields.length; i++) {
                Field field = fields[i];

                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    if (value == null) {
                        continue;
                    }
                    //取值并赋给数组
                    String textValue = value.toString();
                    // 报错：因为get方法是private，所以不能访问
                    //String textValue = field.get(t).toString();
                    csvContent[i] = textValue;
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            //迭代插入记录
            csvWriter.writeRecord(csvContent);
        }
        csvWriter.close();
        System.out.println("<--------CSV文件写入成功-------->");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RequestResultErrorException("请先关闭已经打开的文件！");
        }
    }
}
