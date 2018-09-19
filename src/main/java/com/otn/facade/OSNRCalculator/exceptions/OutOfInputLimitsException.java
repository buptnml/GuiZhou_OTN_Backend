package com.otn.facade.OSNRCalculator.exceptions;

public class OutOfInputLimitsException extends Exception {
    public OutOfInputLimitsException() {
        super("输入功率超过放大器支持范围！\n");
    }

    public OutOfInputLimitsException(String message) {
        super(message + "\n");
    }

}
