package com.bupt.facade.OSNRCalculator.exceptions;

public class OSNRResultOutOfLimitException extends IllegalArgumentException {
    public OSNRResultOutOfLimitException() {
        super("OSNR计算结果小于18dB\n");
    }

}
