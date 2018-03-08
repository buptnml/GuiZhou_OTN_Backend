package com.otn.service.impl;

import com.otn.dao.ResOsnrAmplifierDao;
import com.otn.entity.ResOsnrAmplifier;
import com.otn.pojo.AmplifierCreateInfo;
import com.otn.pojo.AmplifierDTO;
import com.otn.service.AmplifierService;
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

/**
 * Created by caoxiaohong on 17/9/13.
 */
@Service(value = "amplifierService")
class AmplifierServiceImpl implements AmplifierService {
    private final static ExecutorService EXECUTOR = Executors.newWorkStealingPool();
    @Resource
    private ResOsnrAmplifierDao resOsnrAmplifierDao;

    @Override
    public AmplifierDTO updateAmplifiers(Long versionId, Long amplifierID, AmplifierCreateInfo amplifierCreateInfo) {
        if (resOsnrAmplifierDao.selectByExample(getExample(versionId, amplifierID)).size() > 0) {
            ResOsnrAmplifier roa = amplifierDtoToDao(amplifierCreateInfo);
            if (resOsnrAmplifierDao.updateByExampleSelective(roa, getExample(versionId, amplifierID)) > 0) {
                roa = resOsnrAmplifierDao.selectByExample(getExample(versionId, amplifierID)).get(0);
                return amplifierDaoToDto(roa);
            } else {
                throw new NoneUpdateException();
            }
        } else {
            throw new NoneGetException();
        }
    }

    public Example getExample(Long amplifierID) {
        Example condition = new Example(ResOsnrAmplifier.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("versionId", amplifierID);
        return condition;
    }

    private Example getExample(Long versionId, String amplifierName) {
        Example condition = new Example(ResOsnrAmplifier.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("amplifierName", amplifierName);
        return condition;
    }


    private Example getExample(Long versionId, Long amplifierID) {
        Example condition = new Example(ResOsnrAmplifier.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("amplifierId", amplifierID);
        criteria.andEqualTo("versionId", versionId);
        return condition;
    }

    @Override
    @Transactional
    public boolean deleteByAmpid(Long versionId, List<Long> listAmpid) {
        listAmpid.forEach(ampId -> {
            if (resOsnrAmplifierDao.deleteByPrimaryKey(ampId) == 0) {
                throw new NoneRemoveException();
            }
        });
        return true;
    }


    @Override
    @Transactional
    public AmplifierDTO insertAmplifier(Long versionId, AmplifierCreateInfo amplifierCreateInfo) {
        ResOsnrAmplifier roa = amplifierDtoToDao(amplifierCreateInfo);
        roa.setVersionId(versionId);
        if (resOsnrAmplifierDao.insertSelective(roa) < 0) {
            throw new NoneSaveException();
        } else {
            ResOsnrAmplifier result = resOsnrAmplifierDao.selectByExample(getExample(versionId, amplifierCreateInfo
                    .getAmplifierName())).get(0);
            if (null == result) {
                throw new NoneSaveException();
            }
            return amplifierDaoToDto(result);
        }
    }

    @Override
    public AmplifierDTO getAmpById(Long versionId, Long amplifierId) {
        try {
            return amplifierDaoToDto(resOsnrAmplifierDao.selectByExample(getExample(versionId, amplifierId)).get(0));
        } catch (Exception e) {
            throw new NoneGetException("没有在数据库中找到指定的放大器信息!");
        }
    }

    @Override
    public AmplifierDTO getAmpByName(Long versionId, String ampType) {
        return amplifierDaoToDto(resOsnrAmplifierDao.selectByExample(getExample(versionId, ampType)).get(0));
    }

    @Override
    public List<AmplifierDTO> listAmplifiers(Long versionID) {
        List<AmplifierDTO> list = resOsnrAmplifierDao.selectByExample(getExample(versionID)).stream().sorted
                (Comparator.comparing(ResOsnrAmplifier::getGmtModified).reversed()).map(this::amplifierDaoToDto).
                collect(Collectors.toList());
        if (list.size() <= 0) {
            throw new NoneGetException("没有放大器类型数据的相关记录");
        }
        return list;
    }

    @Override
    public int batchRemove(Long versionId) {
        return resOsnrAmplifierDao.deleteByExample(getExample(versionId));
    }

    @Override
    public int batchCreate(Long baseVersionId, Long newVersionId) {
        List<ResOsnrAmplifier> list = resOsnrAmplifierDao.selectByExample(getExample(baseVersionId)).stream().map((ResOsnrAmplifier amp) -> {
            amp.setAmplifierId(null);
            amp.setGmtCreate(null);
            amp.setGmtModified(null);
            amp.setVersionId(newVersionId);
            return amp;
        }).collect(Collectors.toList());
        try {
            return batchInsert(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int batchInsert(List<ResOsnrAmplifier> batchList) throws InterruptedException {
        return BatchDMLUtils.batchDMLAction(batchList, resOsnrAmplifierDao::insertSelective);
//        if (batchList.size() <= 2000) {
//            batchList.forEach(resOsnrAmplifierDao::insertSelective);
//        } else {
//            CountDownLatch count = new CountDownLatch(batchList.size());
//            batchList.parallelStream().forEach(amp -> EXECUTOR.execute(() -> {
//                resOsnrAmplifierDao.insertSelective(amp);
//                count.countDown();
//            }));
//            try {
//                count.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                return batchList.size();
//            }
//        }
//        return batchList.size();
    }


    /**
     * dto转为dao
     *
     * @param amplifierCreateInfo
     * @return
     */
    private ResOsnrAmplifier amplifierDtoToDao(AmplifierCreateInfo amplifierCreateInfo) {
        if (null == amplifierCreateInfo) {
            return null;
        }
        ResOsnrAmplifier result = new ResOsnrAmplifier();
        BeanUtils.copyProperties(amplifierCreateInfo, result);
        return result;
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
