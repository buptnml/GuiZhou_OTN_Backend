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
        //dto转dao
        ResOnsrLinkType rolt=resOnsrLinkTypeDtoToDao(linkTypeDTO);
        rolt.setLinkTypeId(linkTypeId);
        if(resOsnrLinkTypeDao.updateByPrimaryKeySelective(rolt)==0)
            throw new NoneSaveException();
        else{
            return linkTypeDTO;
        }
        //return null;
    }

    @Override
    @Transactional
    public boolean deleteByLinkTypeId(Long versionId,List<Long> linkTypeIds) {
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
    @Transactional
    public LinkTypeDTO createLinkType(Long versionId,LinkTypeDTO linkTypeDTO) {
        //dto转dao
        ResOnsrLinkType resOnsrLinkType=resOnsrLinkTypeDtoToDao(linkTypeDTO);
        resOnsrLinkType.setVersionId(versionId);
        if(resOsnrLinkTypeDao.insertSelective(resOnsrLinkType)==0)
            throw new NoneSaveException();
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
    public List<LinkTypeDTO> selectLinkTypes(Long versionId) {
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
            ResOnsrLinkType rolt=iterator.next();
            //dao转dto
            LinkTypeDTO re=linkTypeDaoToDto(rolt);
            result.add(re);
        }
        return result;
        //return null;
    }

    /**
     * dto转dao
     * @param linkTypeDTO
     * @return
     */
    private ResOnsrLinkType resOnsrLinkTypeDtoToDao(LinkTypeDTO linkTypeDTO){
        ResOnsrLinkType result=new ResOnsrLinkType();
        if(linkTypeDTO==null)
            return result;
        result.setLinkLoss(linkTypeDTO.getLinkLoss());
        result.setLinkRate(linkTypeDTO.getLinkRate());
        result.setLinkType(linkTypeDTO.getLinkType());
        if(linkTypeDTO.getLinkTypeId()!=null)
            result.setLinkTypeId(linkTypeDTO.getLinkTypeId());
        return result;
    }

    /**
     * dao转dto
     * @param resOnsrLinkType
     * @return
     */
    private LinkTypeDTO linkTypeDaoToDto(ResOnsrLinkType resOnsrLinkType){
        LinkTypeDTO result=new LinkTypeDTO();
        if(resOnsrLinkType==null)
            return result;
        result.setLinkTypeId(resOnsrLinkType.getLinkTypeId());
        result.setLinkType(resOnsrLinkType.getLinkType());
        result.setLinkRate(resOnsrLinkType.getLinkRate());
        result.setLinkLoss(resOnsrLinkType.getLinkLoss());
        return result;
    }
}
