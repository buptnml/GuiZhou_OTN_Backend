package com.bupt.service.impl;


import com.bupt.dao.ResOsnrAmplifierDao;
import com.bupt.entity.ResOsnrAmplifier;
import com.bupt.pojo.AmplifierDTO;
import com.bupt.service.DiskTypeService;
import com.bupt.util.exception.controller.result.NoneGetException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service("diskTypeService")
public class DiskTypeServiceImpl implements DiskTypeService {
    @Resource
    private ResOsnrAmplifierDao resOsnrAmplifierDao;

    @Override
    public List<String> listDiskTypes(Long versionId) {
        List<String> result = resOsnrAmplifierDao.selectByExample(getDiskTypesExample(versionId)).stream().map
                (ResOsnrAmplifier::getDiskType).distinct().collect(Collectors.toList());
        if (result.size() == 0) {
            throw new NoneGetException("没有找到机盘类型表相关记录");
        }
        return result;
    }

    @Override
    public List<AmplifierDTO> listAmpsByDiskType(Long versionId, String diskType) {
        List<AmplifierDTO> result = resOsnrAmplifierDao.selectByExample(getAmpsExample(versionId, diskType))
                .stream().map(this::amplifierDaoToDto).collect(Collectors.toList());
        if (result.size() == 0) {
            throw new NoneGetException("没有找到放大器相关记录");
        }
        return result;
    }

    private Example getAmpsExample(Long versionId, String diskTypeName) {
        Example example = new Example(ResOsnrAmplifier.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("diskType", diskTypeName);
        criteria.andEqualTo("versionId", versionId);
        return example;
    }

    private Example getDiskTypesExample(Long versionId) {
        Example example = new Example(ResOsnrAmplifier.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        example.setDistinct(true);
        return example;
    }

    /**
     * dao转为dto
     *
     * @param resOsnrAmplifier
     * @return
     */
    private AmplifierDTO amplifierDaoToDto(ResOsnrAmplifier resOsnrAmplifier) {
        if (resOsnrAmplifier == null) {
            return null;
        }
        AmplifierDTO result = new AmplifierDTO();
        BeanUtils.copyProperties(resOsnrAmplifier, result);
        return result;
    }
}
