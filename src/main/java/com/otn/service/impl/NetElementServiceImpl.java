package com.otn.service.impl;

import com.otn.dao.ResNetElementDao;
import com.otn.entity.ResNetElement;
import com.otn.pojo.NetElementCreateInfo;
import com.otn.pojo.NetElementDTO;
import com.otn.service.NetElementService;
import com.otn.service.utils.BatchDMLUtils;
import com.otn.util.exception.controller.result.NoneGetException;
import com.otn.util.exception.controller.result.NoneRemoveException;
import com.otn.util.exception.controller.result.NoneSaveException;
import com.otn.util.exception.controller.result.NoneUpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service("netElementService")
class NetElementServiceImpl implements NetElementService {
    private final static ExecutorService EXECUTOR = Executors.newWorkStealingPool();
    @Resource
    private ResNetElementDao resNetElementDao;


    @Override
    public NetElementDTO saveNetElement(Long versionId, NetElementCreateInfo netElementCreateInfo) {
        ResNetElement saveInfo = this.convertToResNetElement(netElementCreateInfo);
        saveInfo.setVersionId(versionId);
        if (resNetElementDao.insertSelective(saveInfo) > 0) {
            return convertToNetElementDTO(resNetElementDao.selectByExample(getExample(versionId, netElementCreateInfo
                    .getNetElementName())).get(0));
        }
        throw new NoneSaveException();
    }

    @Override
    @Transactional
    public void listRemoveNetElement(Long versionId, List<Long> netElementIdList) {
        for (Long aNetElementId : netElementIdList) {
            if (resNetElementDao.deleteByExample(getExample(versionId, aNetElementId)) == 0) {
                throw new NoneRemoveException();
            }
        }
    }


    @Override
    public List<NetElementDTO> listNetElement(Long versionId) {
        List<NetElementDTO> resultList = resNetElementDao.selectByExample(getExample(versionId)).stream()
                .sorted(Comparator.comparing(ResNetElement::getGmtModified).reversed()).map
                        (this::convertToNetElementDTO).collect(Collectors.toList());
        if (resultList.size() == 0) {
            throw new NoneGetException("没有找到网元相关记录");
        }
        return resultList;
    }

    @Override
    public List<NetElementDTO> listNetElement(Long versionId, String circleId) {
        return listNetElement(versionId).stream().filter(netElement -> netElement.getCircleId().equals(circleId))
                .collect(Collectors.toList());
    }

    @Override
    public NetElementDTO updateNetElement(Long versionId, Long netElementId, NetElementCreateInfo netElementCreateInfo) {
        ResNetElement updateInfo = this.convertToResNetElement(netElementCreateInfo);
        if (resNetElementDao.updateByExampleSelective(updateInfo, getExample(versionId, netElementId)) == 1) {
            return convertToNetElementDTO(resNetElementDao.selectByPrimaryKey(netElementId));
        }
        throw new NoneUpdateException();
    }

    private Example getExample(Long versionId, Long netElementId) {
        Example example = new Example(ResNetElement.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("netElementId", netElementId);
        return example;
    }

    private Example getExample(Long versionId, String netElementName) {
        Example example = new Example(ResNetElement.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("netElementName", netElementName);
        return example;
    }


    public Example getExample(Long versionId) {
        Example example = new Example(ResNetElement.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        return example;
    }


    @Override
    public int batchRemove(long versionId) {
        return resNetElementDao.deleteByExample(getExample(versionId));
    }

    @Override
    public NetElementDTO getNetElement(Long versionId, long netElementId) {
        if (resNetElementDao.selectByExample(getExample(versionId, netElementId)).size() != 0) {
            return convertToNetElementDTO(resNetElementDao.selectByExample(getExample(versionId, netElementId)).get(0));
        }
        throw new NoneGetException("没有找到对应的网元记录");
    }

    @Override
    public NetElementDTO getNetElement(Long versionId, String netElementName) {
        List<ResNetElement> result = resNetElementDao.selectByExample(getExample(versionId, netElementName));
        if (result.size() == 0) {
            throw new NoneGetException("网元" + netElementName + "不存在");
        }
        return convertToNetElementDTO(result.get(0));
    }

    @Override
    public int batchCreate(Long baseVersionId, Long newVersionId) {
        List<ResNetElement> baseVersionList = resNetElementDao.selectByExample(getExample(baseVersionId)).stream()
                .map(netElement -> {
                    netElement.setNetElementId(null);
                    netElement.setVersionId(newVersionId);
                    netElement.setGmtCreate(null);
                    netElement.setGmtModified(null);
                    return netElement;
                }).collect(Collectors.toList());
        try {
            return batchInsert(baseVersionList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int batchInsert(List<ResNetElement> batchList) throws InterruptedException {
        return BatchDMLUtils.batchDMLActionForEach(batchList, resNetElementDao::insertSelective);
    }


    private NetElementDTO convertToNetElementDTO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        NetElementDTO netElementDTO = new NetElementDTO();
        BeanUtils.copyProperties(inputObject, netElementDTO);
        return netElementDTO;
    }

    private ResNetElement convertToResNetElement(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        ResNetElement resNetElement = new ResNetElement();
        BeanUtils.copyProperties(inputObject, resNetElement);
        return resNetElement;
    }


}
