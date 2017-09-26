package com.bupt.service.impl;

import com.bupt.dao.ResOsnrLinkTypeDao;
import com.bupt.entity.ResOnsrLinkType;
import com.bupt.pojo.LinkTypeDTO;
import com.bupt.service.LinkTypeService;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by caoxiaohong on 17/9/20.
 */
@Service(value = "linkTypeService")
public class LinkTypeServiceImpl implements LinkTypeService {
    @Autowired
    private  ResOsnrLinkTypeDao resOsnrLinkTypeDao;

    @Override
    public LinkTypeDTO updateByLinkTypeId(Long linkTypeId,LinkTypeDTO linkTypeDTO) {
        if(resOsnrLinkTypeDao.selectByPrimaryKey(linkTypeId)==null)
            throw new NoneGetException();
        ResOnsrLinkType rolt=new ResOnsrLinkType();
        rolt.setLinkLoss(linkTypeDTO.getLinkLoss());
        rolt.setLinkRate(linkTypeDTO.getLinkRate());
        rolt.setLinkType(linkTypeDTO.getLinkType());
        rolt.setLinkTypeId(linkTypeId);
        if(resOsnrLinkTypeDao.updateByPrimaryKeySelective(rolt)==0)
            throw new NoneSaveException();
        else{
            linkTypeDTO.setVersionId(linkTypeId);
            return linkTypeDTO;
        }
        //return null;
    }

    @Override
    @Transactional
    public boolean deleteByLinkTypeId(List<Long> linkTypeIds) {
        if(linkTypeIds.size()==0)
            return true;
        Iterator<Long> iterator=linkTypeIds.iterator();
        while (iterator.hasNext()){
            if(resOsnrLinkTypeDao.deleteByPrimaryKey(iterator.next())==0){
                throw new NoneRemoveException();
            }
        }
        return true;
    }

    @Override
    public boolean deleteByVersionId(Long versionId) {
        //删除条件
        Example condition=new Example(ResOnsrLinkType.class);
        Example.Criteria criteria=condition.createCriteria();
        criteria.andEqualTo("versionId",versionId);
        //查找是否存在
        if(resOsnrLinkTypeDao.selectByExample(condition).size()==0)
            throw new NoneGetException();
        //删除
        if(resOsnrLinkTypeDao.deleteByExample(condition)==0)
            return false;
        return true;
    }

    @Override
    public LinkTypeDTO createLinkType(Long versionId,LinkTypeDTO linkTypeDTO) {
        ResOnsrLinkType resOnsrLinkType=new ResOnsrLinkType();
        resOnsrLinkType.setLinkLoss(linkTypeDTO.getLinkLoss());
        resOnsrLinkType.setLinkRate(linkTypeDTO.getLinkRate());
        resOnsrLinkType.setLinkType(linkTypeDTO.getLinkType());
        resOnsrLinkType.setVersionId(versionId);
        if(resOsnrLinkTypeDao.insertSelective(resOnsrLinkType)==0)
            throw new NoneSaveException();
        linkTypeDTO.setVersionId(versionId);
        //获取linkTypeId
        Example condition=new Example(ResOnsrLinkType.class);
        Example.Criteria criteria=condition.createCriteria();
        criteria.andEqualTo("linkLoss",linkTypeDTO.getLinkLoss());
        criteria.andEqualTo("linkRate",linkTypeDTO.getLinkRate());
        criteria.andEqualTo("linkType",linkTypeDTO.getLinkType());
        criteria.andEqualTo("versionId",versionId);

        List<ResOnsrLinkType> result=resOsnrLinkTypeDao.selectByExample(condition);
        if(result.size()!=1){
            throw new  NoneSaveException();
        }
        linkTypeDTO.setLinkTypeId(result.get(0).getLinkTypeId());
        return linkTypeDTO;
        //return null;
    }

    @Override
    public List<LinkTypeDTO> retrieveLinkTypes(Long versionId) {
        //查询条件
        List<LinkTypeDTO> result=new ArrayList<LinkTypeDTO>();
        Example condition=new Example(ResOnsrLinkType.class);
        Example.Criteria criteria=condition.createCriteria();
        criteria.andEqualTo("versionId",versionId);

        List<ResOnsrLinkType> tmp=resOsnrLinkTypeDao.selectByExample(condition);
        if(tmp.size()==0)
            return null;
        Iterator<ResOnsrLinkType> iterator=tmp.iterator();
        while(iterator.hasNext()){
            LinkTypeDTO re=new LinkTypeDTO();
            ResOnsrLinkType rolt=iterator.next();
            re.setLinkLoss(rolt.getLinkLoss());
            re.setLinkRate(rolt.getLinkRate());
            re.setLinkType(rolt.getLinkType());
            re.setLinkTypeId(rolt.getLinkTypeId());
            result.add(re);
        }
        return result;
        //return null;
    }
}
