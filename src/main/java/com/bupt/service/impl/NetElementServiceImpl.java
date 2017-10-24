package com.bupt.service.impl;

import com.bupt.dao.ResBussinessDao;
import com.bupt.dao.ResLinkDao;
import com.bupt.dao.ResNetElementDao;
import com.bupt.entity.ResBussiness;
import com.bupt.entity.ResLink;
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
import java.util.List;

@Service("netElementService")
public class NetElementServiceImpl implements NetElementService {
    @Resource
    private ResNetElementDao resNetElementDao;
    @Resource
    private ResBussinessDao resBussinessDao;
    @Resource
    private ResLinkDao resLinkDao;

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
        for (Long aNetElementId : netElementIdList) {
            removeReferBussiness(versionId, resNetElementDao.selectByPrimaryKey(aNetElementId).getNetElementName());
            removeFromNetElement(versionId, aNetElementId);
            if (resNetElementDao.deleteByExample(getExample(versionId, aNetElementId)) == 0) {
                throw new NoneRemoveException();
            }
        }
    }

    //删除链路中和这个网元相关联的链路
    private void removeFromNetElement(Long versionId, Long netElementId) {
        resLinkDao.deleteByExample(getExample(versionId, "endAId", netElementId));
        resLinkDao.deleteByExample(getExample(versionId, "endZId", netElementId));
    }

    //删除业务（光通道）中和这个网元相关联的业务（光通道）
    private void removeReferBussiness(Long versionId, String referString) {
        resBussinessDao.deleteByExample(getExample(versionId, "mainRoute", referString));
        resBussinessDao.deleteByExample(getExample(versionId, "spareRoute", referString));
    }

    private Example getExample(Long versionId, String condition, Long nodeId) {
        Example removeExample = new Example(ResLink.class);
        Example.Criteria criteria = removeExample.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo(condition, nodeId);
        return removeExample;
    }


    private Example getExample(Long versionId, String condition, String referString) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo(condition, "%" + referString + "%");
        return example;
    }

    @Override
    public List<NetElementDTO> listNetElement(Long versionId) {
        List<ResNetElement> resNetElementsList = resNetElementDao.selectByExample(getExample(versionId));
        if (resNetElementsList.size() == 0) {
            throw new NoneGetException();
        }

        List<NetElementDTO> resultList = new ArrayList<>();
        for (ResNetElement aResNetElementsList : resNetElementsList) {
            resultList.add(this.convertToNetElementDTO(aResNetElementsList));
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
        if (null != resNetElementDao.selectByExample(getExample(versionId, netElementId))) {
            return convertToNetElementDTO(resNetElementDao.selectByExample(getExample(versionId, netElementId)));
        }
        throw new NoneGetException();
    }

    @Override
    public NetElementDTO getNetElement(Long versionId, String netElementName) {
        ResNetElement result = resNetElementDao.selectByExample(getExample(versionId, netElementName)).get(0);
        if (null != result) {
            return convertToNetElementDTO(result);
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
