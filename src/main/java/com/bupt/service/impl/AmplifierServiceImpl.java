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
            ResOsnrAmplifier roa=amplifierDtoToDao(amplifier);
            roa.setAmplifierId(amplifierID);
            if(resOsnrAmplifierDao.updateByPrimaryKeySelective(roa)>0){
                roa=resOsnrAmplifierDao.selectByPrimaryKey(amplifierID);
                //dao转dto
                AmplifierDTO result=amplifierDaoToDto(roa);
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
    @Transactional
    public  boolean deleteByAmpid(Long versionId,List<Long> listAmpid){
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
    @Transactional
    public AmplifierDTO insertAmplifier(Long versionId,AmplifierDTO amplifer) {
        //dto转dao
        ResOsnrAmplifier roa=amplifierDtoToDao(amplifer);
        roa.setVersionId(versionId);
        //BeanUtils.copyProperties(amplifer,roa);
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
        //dao转dto
        List<AmplifierDTO> result=new ArrayList<AmplifierDTO>();
        for(ResOsnrAmplifier i:list){
            AmplifierDTO amplifierDTO=amplifierDaoToDto(i);
            result.add(amplifierDTO);
        }
        return result;
    }

    /**dto转为dao
     * @param amplifierDTO
     * @return
     */
    private ResOsnrAmplifier amplifierDtoToDao(AmplifierDTO amplifierDTO){
        ResOsnrAmplifier result=new ResOsnrAmplifier();
        if(amplifierDTO==null)
            return result;
        result.setAmplifierId(amplifierDTO.getAmplifierID());
        result.setAmplifierName(amplifierDTO.getAmplifierName());
        result.setGain(amplifierDTO.getGain());
        result.setMinimumInputPower(amplifierDTO.getMinimumInputPower());
        result.setMaximumOutputPower(amplifierDTO.getMaximumOutputPower());
        result.setMaximumInputPower(amplifierDTO.getMaximumInputPower());
        return result;
    }

    /**dao转为dto
     * @param resOsnrAmplifier
     * @return
     */
    private AmplifierDTO amplifierDaoToDto(ResOsnrAmplifier resOsnrAmplifier){
        AmplifierDTO result=new AmplifierDTO();
        if(resOsnrAmplifier==null)
            return result;
        result.setAmplifierID(resOsnrAmplifier.getAmplifierId());
        result.setAmplifierName(resOsnrAmplifier.getAmplifierName());
        result.setGain(resOsnrAmplifier.getGain());
        result.setMaximumInputPower(resOsnrAmplifier.getMaximumInputPower());
        result.setMaximumOutputPower(resOsnrAmplifier.getMaximumOutputPower());
        result.setMinimumInputPower(resOsnrAmplifier.getMinimumInputPower());
        return result;
    }
}
