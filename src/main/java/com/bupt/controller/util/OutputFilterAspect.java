package com.bupt.controller.util;


import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 该切面的目的是对Controller层输入的数据进行一些数据整理工作
 * 现在暂时只有一个目标：将所有参数为Date类型的值从Date数字转化为Date类型
 */

@Component
@Aspect
public class OutputFilterAspect {

}
