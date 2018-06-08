package com.otn.facade.OSNRCalculator.exceptions;

import com.otn.util.exception.controller.input.IllegalArgumentException;

public class OutOfInputLimitsException extends IllegalArgumentException {
    public OutOfInputLimitsException() {
        super("输入功率超过放大器支持范围！\n");
    }

    public OutOfInputLimitsException(String message) {
        super(message + "\n");
    }

}
