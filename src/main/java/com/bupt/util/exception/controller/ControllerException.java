package com.bupt.util.exception.controller;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 控制层异常，处理所有控制层异常
 */
public class ControllerException extends RuntimeException {

    public ControllerException() {
        super();
    }

    public ControllerException(String message) {
        super(message);
    }
}
