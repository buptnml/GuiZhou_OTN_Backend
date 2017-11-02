package com.bupt.controller.utils;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 在某些极端情况下，可能出现允许Controller中函数入参为null的情况（有别于入参对象内部属性为null）
 * eg ： VersionController的dataSynchronize函数入参，出于某种考虑，设计为允许versionId这个参数为null的情况
 * 入参检查的切面InputFilterAspect无法判断这种情况
 * 加入这个注解在class上或者在函数上，入参检查的切面就不会再检查该类或者函数的入参，回避了上面的情况
 * 但是相对的，入参的安全性需要由该类或者该函数自己保证
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InputCheckException {
    String reason() default "加入注解的原因";
}
