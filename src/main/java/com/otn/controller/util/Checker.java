package com.otn.controller.util;


import com.otn.pojo.VersionDictDTO;
import com.otn.service.VersionBasicService;
import com.otn.service.VersionDictService;
import com.otn.util.exception.controller.input.IllegalArgumentException;
import com.otn.util.exception.controller.input.NullArgumentException;
import com.otn.webservice.com.pojo.WebServiceConfigInfo;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 检查工具，主要用来检查一些Controller层基础的数据检查
 */
@Component
public class Checker {
    /*Spring是基于对象层面上的依赖注入，静态变量不是对象的属性，而是一个类的属性
    * 通过上面的方法可以实现类似静态注入的效果*/
    private static Checker checkerFactory;
    @Resource
    private VersionBasicService versionBasicService;
    @Resource
    private VersionDictService versionDictService;
    @Resource
    private WebServiceConfigInfo webServiceConfigInfo;

    /**
     * 检查Object和Object内部的属性是否合法
     *
     * @param object
     */
    public static void checkObject(Object object) {
        if (null == object) {
            if (null == object.getClass().getAnnotation(Nullable.class)) {
                throw new NullArgumentException("输入的信息不能为空！");
            }
            return;
        }
        if (object.getClass().isPrimitive()) {
            /*原始数据类型不需要检查，直接返回*/
            return;
        }
        if (object instanceof Character || object instanceof Boolean || object instanceof Number) {
            /*包装类型不需要进一步检查*/
            return;
        }
        /*字符串需要单独判断是否为空字符串*/
        if (object instanceof String) {
//            if (object.equals("")) {
//                throw new NullArgumentException("输入的信息不能为空！");
//            }
            return;
        }
        if (object instanceof Collection) {
            /*首先判断输入类型是否为集合数据类型
            若为集合类型，则需要检查长度和内容
            然后递归的检查全部对象
            */
            if (((Collection) object).size() == 0) {
                throw new IllegalArgumentException("输入的信息不能为空！");
            }
            ((Collection) object).forEach(Checker::checkObject);
            return;
        }
        /*最后要判断是否为数组类型,若为数组类型需要逐个检查*/
        try {
            if (Array.getLength(object) == 0) {
                throw new NullArgumentException("输入的信息不能为空！");
            } else {
                for (int i = 0; i < Array.getLength(object); i++) {
                    checkObject(Array.get(object, i));
                }
                return;
            }
        } catch (java.lang.IllegalArgumentException ignored) {

        }
        checkFields(object);
    }

    /**
     * 检查object中的属性
     *
     * @param object
     */
    private static void checkFields(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field f : fields) {
            try {
            /*1.首先判断是否为原始类型，原始类型不需要检查*/
                if (!f.getClass().isPrimitive()) {
                    /*2.判断是否为null，若为null检查是否有Nullable注解，没有则该参数不允许为空*/
                    if (null == getMethod(object, f).invoke(object)) {
                        if (null == f.getAnnotation(Nullable.class)) {
                            throw new NullArgumentException("输入的信息不能为空！" + f.getName());
                        }
                    } else {
                        checkObject(getMethod(object, f).invoke(object));
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static Method getMethod(Object o, Field f) {
        try {
            return o.getClass().getMethod("get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检查指定版本下指定类型数据的操作是否合法
     *
     * @param versionId
     * @param dataEnum
     */
    public static void checkVersion(Long versionId, VersionDictEnum dataEnum) {
        if (versionId.equals(checkerFactory.webServiceConfigInfo.getBASIC_VERSION_ID())) {
            throw new IllegalArgumentException("不允许对基础版本的数据做任何修改操作！");
        }
        if (null == dataEnum) { //匹配失败就返回
            return;
        }
        VersionDictDTO versionDictDTO = checkerFactory.versionDictService.getVersionDictByName(checkerFactory
                .versionBasicService.getVersion(versionId).getVersionDictName());
        switch (dataEnum) {
            case disk: {
                if (!versionDictDTO.getHasDisk()) {
                    throw new IllegalArgumentException("该版本没有机盘信息，请重新选择版本！");
                }
                break;
            }
            case link: {
                if (!versionDictDTO.getHasLink()) {
                    throw new IllegalArgumentException("该版本没有链路信息，请重新选择版本！");
                }
                break;
            }
            case linkType: {
                if (!versionDictDTO.getHasLinkType()) {
                    throw new IllegalArgumentException("该版本没有链路类型信息，请重新选择版本！");
                }
                break;
            }
            case amplifier: {
                if (!versionDictDTO.getHasAmplifier()) {
                    throw new IllegalArgumentException("该版本没有机盘信息，请重新选择版本！");
                }
                break;
            }
            case bussiness: {
                if (!versionDictDTO.getHasBussiness()) {
                    throw new IllegalArgumentException("该版本没有光通道信息，请重新选择版本！");
                }
                break;
            }
            case netElement: {
                if (!versionDictDTO.getHasNetElement
                        ()) {
                    throw new IllegalArgumentException("该版本没有网元信息，请重新选择版本！");
                }
                break;
            }
        }
    }


    @PostConstruct
    private void init() {
        checkerFactory = this;
    }
}


