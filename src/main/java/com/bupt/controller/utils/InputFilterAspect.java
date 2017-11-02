package com.bupt.controller.utils;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.EnumSet;


@Component
@Aspect
public class InputFilterAspect {

    @Before(value = "(execution(* com.bupt.controller.*Controller.*(..)) && args(..))")
    public void InputFilter(JoinPoint point) throws Exception {
        String[] paramNames = ((CodeSignature) point.getSignature()).getParameterNames();
        Object[] args = point.getArgs();
        /*上面这两个数组是长度相同，一一对应的，一个代表变量名称，一个代表变量本身*/
        Class targetClass = point.getSignature().getDeclaringType();
        if (null == ((MethodSignature) point.getSignature()).getMethod().getAnnotation(InputCheckException.class)) {
            for (int i = 0; i < paramNames.length; i++) {
                ControllerChecker.checkObject(args[i]);
                if (paramNames[i].trim().toLowerCase().equals("versionid")
                        && null == targetClass.getAnnotation(VersionCheckException.class)
                        && null == ((MethodSignature) point.getSignature()).getMethod().getAnnotation
                        (VersionCheckException.class)) {
                    /*涉及到版本的操作，在类和方法上均无指定的Exceptional注解的情况下，需要验证版本信息*/
                    ControllerChecker.checkVersion((Long) args[i], enumFactory(targetClass.getName()));
                }
            }
        }
    }

    /**
     * 协助进行版本检查，会通过输入的字符串来估计可能是属于版本中的哪一个类型
     * eg : className = "com.bupt.controller.AmplifierController$$EnhancerBySpringCGLIB$$9588b98d"
     * 由于其中含有Amplifier字段，会生成一个amplifier enum
     *
     * @param className
     * @return
     */
    private VersionDictEnum enumFactory(String className) throws Exception {
        for (VersionDictEnum dataEnum : EnumSet.range(VersionDictEnum.bussiness, VersionDictEnum.amplifier)) {
            if (className.toLowerCase().trim().contains(dataEnum.toString().toLowerCase().trim())) {
                return dataEnum;
            }
        }
        return null;
    }
}


