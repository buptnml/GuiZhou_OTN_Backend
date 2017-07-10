package com.bupt.util.exception.controller.input;

import com.bupt.util.exception.controller.ControllerException;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 非法输入异常
 */
public class IllegalArgumentException extends ControllerException {
    public IllegalArgumentException() {
        super();
    }

    public IllegalArgumentException(String message) {
        super(message);
    }
}
