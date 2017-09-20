package com.bupt.service.impl;

import com.bupt.dao.ResNetElementDao;
import com.bupt.entity.ResNetElement;
import com.bupt.pojo.NetElementCreateInfo;
import com.bupt.pojo.NetElementDTO;
import com.bupt.service.NetElementService;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("netElementService")
public class NetElementServiceImpl implements NetElementService {
    @Resource
    private ResNetElementDao resNetElementDao;

    @Override
    public NetElementDTO saveNetElement(Long versionId, NetElementCreateInfo netElementCreateInfo) {
        ResNetElement saveInfo = this.convertToResNetElement(netElementCreateInfo);
        saveInfo.setVersionId(versionId);
        if (resNetElementDao.insertSelective(saveInfo) > 0) {
            return convertToNetElementDTO(resNetElementDao.selectOne(saveInfo));
        }
        throw new NoneSaveException();
    }

    @Override
    @Transactional
    public void listRemoveNetElement(Long versionId, List<Long> netElementIdList) {
        Iterator<Long> idListIterator = netElementIdList.iterator();
        while (idListIterator.hasNext()) {
            if (resNetElementDao.deleteByExample(getExample(versionId, idListIterator.next())) == 0) {
                throw new NoneRemoveException();
            }
        }
    }

    @Override
    public List<NetElementDTO> listNetElement(Long versionId) {
        List<ResNetElement> resNetElementsList = resNetElementDao.selectByExample(getExample(versionId));
        if (resNetElementsList.size() == 0) {
            throw new NoneGetException();
        }

        List<NetElementDTO> resultList = new ArrayList<>();
        for (int j = 0; j < resNetElementsList.size(); j++) {
            resultList.add(this.convertToNetElementDTO(resNetElementsList.get(j)));
        }
        return resultList;
    }

    @Override
    public NetElementDTO updateNetElement(Long versionId, Long netElementId, NetElementCreateInfo netElementCreateInfo) {
        ResNetElement updateInfo = this.convertToResNetElement(netElementCreateInfo);
        if (resNetElementDao.updateByExampleSelective(updateInfo, getExample(versionId, netElementId)) == 1) {
            return convertToNetElementDTO(resNetElementDao.selectOne(updateInfo));
        }
        throw new NoneUpdateException();
    }

    private Example getExample(Long versionId, Long netElementId) {
        Example updateExample = new Example(ResNetElement.class);
        Example.Criteria criteria = updateExample.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("netElementId", netElementId);
        return updateExample;
    }


    private Example getExample(Long versionId) {
        Example example = new Example(ResNetElement.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        return example;
    }


    @Override
    public void batchRemove(long versionId) {
        resNetElementDao.deleteByExample(getExample(versionId));
    }

    @Override
    public NetElementDTO getNetElement(Long versionId, long netElementId) {
        if (null != resNetElementDao.selectByPrimaryKey(getExample(versionId, netElementId))) {
            return convertToNetElementDTO(resNetElementDao.selectByExample(getExample(versionId, netElementId)));
        }
        throw new NoneGetException();
    }

    @Override
    public void batchCreate(Long baseVersionId, Long newVersionId) {
        List<ResNetElement> baseVersionList = resNetElementDao.selectByExample(getExample(baseVersionId));
        for (ResNetElement disk : baseVersionList) {
            NetElementCreateInfo newNetElement = new NetElementCreateInfo(disk.getCoordinateX(), disk.getCoordinateY()
                    , disk.getNetElementName(), disk.getNetElementType());
            saveNetElement(newVersionId, newNetElement);
        }
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
