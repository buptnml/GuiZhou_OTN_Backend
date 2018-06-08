package com.otn.util.exception.controller.input;

import com.otn.util.exception.controller.ControllerException;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 非法输入异常
 */
public class IllegalArgumentException extends ControllerException {
    public IllegalArgumentException() {
        super("输入参数非法！\n");
    }

    public IllegalArgumentException(String message) {
        super(message);
    }
}
