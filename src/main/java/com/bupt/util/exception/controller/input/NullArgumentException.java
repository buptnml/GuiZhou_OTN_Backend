package com.bupt.util.exception.controller.input;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 空指针参数异常
 */
public class NullArgumentException extends IllegalArgumentException {
    public NullArgumentException() {
        super("Null argument。\n");
    }

    public NullArgumentException(String message) {
        super("Null argument：" + message + "\n");
    }
}
