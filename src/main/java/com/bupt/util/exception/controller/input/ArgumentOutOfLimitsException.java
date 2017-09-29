package com.bupt.util.exception.controller.input;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 参数超限异常
 */
public class ArgumentOutOfLimitsException extends IllegalArgumentException {
    public ArgumentOutOfLimitsException() {
        super("Argument out of limit！.\n");
    }

    public ArgumentOutOfLimitsException(String message) {
        super("Argument out of limit：" + message + "\n");
    }
}
