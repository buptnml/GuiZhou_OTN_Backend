package com.bupt.service.impl;

import com.bupt.dao.ResOsnrAmplifierDao;
import com.bupt.entity.ResOsnrAmplifier;
import com.bupt.pojo.AmplifierDTO;
import com.bupt.service.AmplifierService;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by caoxiaohong on 17/9/13.
 */
@Service(value = "amplifierService")
public class AmplifierServiceImpl implements AmplifierService {
    @Autowired
    private ResOsnrAmplifierDao resOsnrAmplifierDao;

    @Override
    public AmplifierDTO updateAmplifiers(Long amplifierID,AmplifierDTO amplifier) {
        //查询条件
        Example condition=new Example(ResOsnrAmplifier.class);
        Example.Criteria cirteria=condition.createCriteria();
        cirteria.andEqualTo("amplifierId",amplifierID);

        if(resOsnrAmplifierDao.selectByExample(condition).size()>0){
            //dto转dao
            ResOsnrAmplifier roa=new ResOsnrAmplifier();
            //BeanUtils.copyProperties(amplifier,roa);
            roa.setAmplifierName(amplifier.getAmplifierName());
            roa.setGain(amplifier.getGain());
            roa.setMaximumInputPower(amplifier.getMaximumInputPower());
            roa.setMaximumOutputPower(amplifier.getMaximumOutputPower());
            roa.setMinimumInputPower(amplifier.getMinimumInputPower());
            roa.setAmplifierId(amplifierID);

            if(resOsnrAmplifierDao.updateByPrimaryKeySelective(roa)>0){
                AmplifierDTO result=new AmplifierDTO();
                roa=resOsnrAmplifierDao.selectByPrimaryKey(amplifierID);
                result.setAmplifierID(roa.getAmplifierId());
                result.setAmplifierName(roa.getAmplifierName());
                result.setGain(roa.getGain());
                result.setMaximumInputPower(roa.getMaximumInputPower());
                result.setMaximumOutputPower(roa.getMaximumOutputPower());
                result.setMinimumInputPower(roa.getMinimumInputPower());
                //result.setVersionId(roa.getVersionId());
                return result;
            }else{
                throw  new NoneUpdateException();
            }
        }else{
            throw new NoneGetException();
        }
        //return null;
    }

    @Override
    public boolean deleteByVid(Long vid) {
        Example condition=new Example(ResOsnrAmplifier.class);
        Example.Criteria criteria=condition.createCriteria();

        criteria.andEqualTo("versionId",vid);
        if(resOsnrAmplifierDao.deleteByExample(condition)<0){
            throw new  NoneRemoveException();
        }
        return true;
        //return false;
    }

    @Override
    @Transactional
    public  boolean deleteByAmpid(List<Long> listAmpid){
        //只能按照迭代器方式删除,不能for删除
        Iterator<Long> list=listAmpid.listIterator();
        while (list.hasNext()){
            if(resOsnrAmplifierDao.deleteByPrimaryKey(list.next())==0){
                throw new NoneRemoveException();
            }
        }
        return true;
    }


    @Override
    public AmplifierDTO insertAmplifier(Long versionId,AmplifierDTO amplifer) {
        //dto传输输入给dao
        //amplifer.setVersionId(versionId);
        ResOsnrAmplifier roa=new ResOsnrAmplifier();
        //BeanUtils.copyProperties(amplifer,roa);
        roa.setAmplifierName(amplifer.getAmplifierName());
        roa.setGain(amplifer.getGain());
        roa.setMaximumInputPower(amplifer.getMaximumInputPower());
        roa.setMaximumOutputPower(amplifer.getMaximumOutputPower());
        roa.setMinimumInputPower(amplifer.getMinimumInputPower());
        roa.setVersionId(versionId);
        if(resOsnrAmplifierDao.insertSelective(roa)<0){
            throw  new NoneSaveException();
        }else{
            //获取AmplifierID
            Example condition=new Example(ResOsnrAmplifier.class);
            Example.Criteria criteria=condition.createCriteria();
            criteria.andEqualTo("amplifierName",amplifer.getAmplifierName());
            criteria.andEqualTo("gain",amplifer.getGain());
            criteria.andEqualTo("maximumInputPower",amplifer.getMaximumInputPower());
            criteria.andEqualTo("maximumOutputPower",amplifer.getMaximumOutputPower());
            criteria.andEqualTo("versionId",versionId);

            List<ResOsnrAmplifier> result=resOsnrAmplifierDao.selectByExample(condition);
            if(result.size()!=1)
                throw new NoneSaveException();
            amplifer.setAmplifierID(result.get(0).getAmplifierId());
            return amplifer;
        }
        //return null;
    }

    @Override
    public List<AmplifierDTO> selectAmplifiers(Long versionID) {
        Example condition=new Example(ResOsnrAmplifier.class);
        Example.Criteria criteria=condition.createCriteria();
        criteria.andEqualTo("versionId",versionID);

        List<ResOsnrAmplifier> list= resOsnrAmplifierDao.selectByExample(condition);
        if(list.size()<=0){
            throw new NoneGetException();
        }
        //dao传数据到dto
        List<AmplifierDTO> result=new ArrayList<AmplifierDTO>();
        for(ResOsnrAmplifier i:list){
            AmplifierDTO amplifierDTO=new AmplifierDTO();
            amplifierDTO.setAmplifierID(i.getAmplifierId());
            amplifierDTO.setAmplifierName(i.getAmplifierName());
            amplifierDTO.setGain(i.getGain());
            amplifierDTO.setMaximumInputPower(i.getMaximumInputPower());
            amplifierDTO.setMaximumOutputPower(i.getMaximumOutputPower());
            amplifierDTO.setMinimumInputPower(i.getMinimumInputPower());
            result.add(amplifierDTO);
        }
        return result;
    }
}
