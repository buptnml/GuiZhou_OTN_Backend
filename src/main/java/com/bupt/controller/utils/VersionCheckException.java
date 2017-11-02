package com.bupt.controller.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * VersionCheckException 注解，
 * 自定义注解，由于现在加入了一个新的切面InputFilterAspect
 * 该切面会拦截除了ExceptionHandlerController外所有Controller的函数并检查其参数的合法性，
 * 同时会检查versionId的合法性
 * 在函数或者类上加入该注解可以避免检查该版本id的合法性
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VersionCheckException {
    String reason() default "加入注解的原因";
}
