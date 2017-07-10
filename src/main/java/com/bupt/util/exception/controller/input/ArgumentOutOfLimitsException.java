package com.bupt.util.exception.controller.input;

/**
 * Created by 韩宪斌 on 2017/6/30.
 */
public class ArgumentOutOfLimitsException extends IllegalArgumentException {
    public ArgumentOutOfLimitsException() {
        super("The argument is out of limit.\n");
    }

    public ArgumentOutOfLimitsException(String message) {
        super("The argument "+ message + " is out of limit.\n");
    }
}
