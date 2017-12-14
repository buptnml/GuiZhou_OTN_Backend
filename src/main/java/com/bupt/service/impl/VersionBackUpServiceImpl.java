package com.bupt.service.impl;

import com.bupt.dao.*;
import com.bupt.entity.SysBackUp;
import com.bupt.entity.SysVersion;
import com.bupt.service.VersionBackUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

@Service("versionBackUpService")
public class VersionBackUpServiceImpl implements VersionBackUpService {
    @Resource
    private ResDiskDao resDiskDao;
    @Resource
    private ResLinkDao resLinkDao;
    @Resource
    private ResBussinessDao resBussinessDao;
    @Resource
    private ResOsnrAmplifierDao resOsnrAmplifierDao;
    @Resource
    private ResOsnrLinkTypeDao resOsnrLinkTypeDao;
    @Resource
    private ResNetElementDao resNetElementDao;
    @Resource
    private SysVersionBackUpDao sysVersionBackUpDao;

    @Override
    public void initBackUp(Long versionId) {
        SysBackUp createInfo = new SysBackUp();
        createInfo.setVersionId(versionId);
        if (checkVersionBackUpExits(versionId) || sysVersionBackUpDao.insertSelective(createInfo) != 1) {
            throw new RuntimeException("版本备份信息管理出现严重错误，请联系后台管理！");
        }
    }

    @Override
    public void removeBackUp(Long versionId) {
        if (sysVersionBackUpDao.deleteByExample(getExample(versionId)) != 1) {
            throw new RuntimeException("版本备份信息管理出现严重错误，请联系后台管理！");
        }
    }

    @Override
    public void saveBackUp(Long versionId) {
        //条目要先存在再保存
        if (!checkVersionBackUpExits(versionId)) {
            initBackUp(versionId);
        }
        sysVersionBackUpDao.updateByPrimaryKeySelective(backUpObjectFactory(versionId));
    }

    private SysBackUp backUpObjectFactory(Long versionId) {
        SysBackUp createInfo = new SysBackUp();
        createInfo.setVersionId(versionId);
        //todo 修改为多线程并发
        createInfo.setBussinessBackUp(ByteTransferFactory.toByteArray(resBussinessDao.selectByExample(getExample(versionId))));
        createInfo.setDiskBackUp(ByteTransferFactory.toByteArray(resDiskDao.selectByExample(getExample(versionId))));
        createInfo.setLinkBackUp(ByteTransferFactory.toByteArray(resLinkDao.selectByExample(getExample(versionId))));
        createInfo.setNetElementBackUp(ByteTransferFactory.toByteArray(resNetElementDao.selectByExample(getExample(versionId)
        )));
        createInfo.setOsnrLinkTypeBackUp(ByteTransferFactory.toByteArray(resOsnrLinkTypeDao.selectByExample
                (getExample(versionId))));
        createInfo.setOsnrAmplifierBackUp(ByteTransferFactory.toByteArray(resOsnrAmplifierDao.selectByExample
                (getExample(versionId))));
        return createInfo;
    }


    @Override
    @Transactional
    public void restoreBackUp(Long versionId) {
        SysBackUp backUpInfo = sysVersionBackUpDao.selectByPrimaryKey(versionId);
        restoreMachine(ByteTransferFactory.toObject(backUpInfo.getBussinessBackUp()), versionId,
                resBussinessDao);
        restoreMachine(ByteTransferFactory.toObject(backUpInfo.getNetElementBackUp()), versionId, resNetElementDao);
        restoreMachine(ByteTransferFactory.toObject(backUpInfo.getDiskBackUp()), versionId, resDiskDao);
        restoreMachine(ByteTransferFactory.toObject(backUpInfo.getLinkBackUp()), versionId, resLinkDao);
        restoreMachine(ByteTransferFactory.toObject(backUpInfo.getOsnrLinkTypeBackUp()), versionId, resOsnrLinkTypeDao);
        restoreMachine(ByteTransferFactory.toObject(backUpInfo.getOsnrAmplifierBackUp()), versionId, resOsnrAmplifierDao);
    }

    /**
     * 进行恢复操作的函数
     *
     * @param backUpDataList
     * @param versionId
     * @param mapperDao
     * @param <K>
     */
    private <K> void restoreMachine(List<K> backUpDataList, Long versionId, Mapper mapperDao) {
        if (null == backUpDataList) return;
        List<K> newDataList = mapperDao.selectByExample(getExample(versionId));
        if (backUpDataList.size() == 0 && newDataList.size() == 0) {
            //新数据为空，备份数据为空，没有恢复的必要
            return;
        }
        if (newDataList.size() != backUpDataList.size()) {
            //两个列表数据不一致，恢复
            mapperDao.deleteByExample(getExample(versionId));
            backUpDataList.forEach(obj -> mapperDao.insert(obj));
            return;
        }
        if (newDataList.size() == backUpDataList.size()) {
            //无法判断的时候，两个列表都是以gmtModified倒序排列
            //比较第0个条目的时间，如果不一样肯定是不一样的数据，进行恢复操作
            if (!newDataList.get(0).equals(backUpDataList.get(0))) {
                mapperDao.deleteByExample(getExample(versionId));
                backUpDataList.forEach(obj -> mapperDao.insert(obj));
            }
        }
    }


    private boolean checkVersionBackUpExits(Long versionId) {
        return sysVersionBackUpDao.selectByExample(getExample(versionId)).size() == 1;
    }

    private Example getExample(Long versionId) {
        Example example = new Example(SysVersion.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("gmtModified").desc();//所有Example均以最后修改时间倒序
        criteria.andEqualTo("versionId", versionId);
        return example;
    }


    private static class ByteTransferFactory {
        private static Logger logger = LoggerFactory.getLogger(ByteTransferFactory.class);

        /**
         * 序列化List为二进制流
         */
        static byte[] toByteArray(Object object) {
            if (null == object) return null;
            byte[] bytes = null;
            ByteArrayOutputStream bos;
            ObjectOutputStream oos;
            try {
                bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(object);
                bytes = bos.toByteArray();
                oos.flush();
                oos.close();
                bos.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
            return bytes;
        }

        /**
         * 反序列化二进制流为List
         */
        static <K> List<K> toObject(byte[] bytes) {
            if (null == bytes) return null;
            Object result = null;
            ObjectInputStream ois;
            ByteArrayInputStream bis;
            try {
                bis = new ByteArrayInputStream(bytes);
                ois = new ObjectInputStream(bis);
                result = ois.readObject();
                bis.close();
                ois.close();
            } catch (IOException | ClassNotFoundException ex) {
                logger.error(ex.getMessage());
            }
            return (List<K>) result;
        }
    }
}
