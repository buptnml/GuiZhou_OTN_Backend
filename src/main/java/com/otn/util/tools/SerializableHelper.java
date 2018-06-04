package com.otn.util.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * @Auther: 李景然
 * @Date: 2018/5/28 18:33
 * @Description:
 */
public class SerializableHelper {
    private static SerializableHelper helper;

    private SerializableHelper(){}

    public static SerializableHelper getHelper(){
        if(helper==null){
            helper=new SerializableHelper();
        }
        return helper;
    }

    private static Logger logger = LoggerFactory.getLogger(SerializableHelper.class);

    /**
     * 序列化List为二进制流
     */
    public static byte[] toByteArray(Object object) {
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
    public static <K> List<K> toObject(byte[] bytes) {
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
