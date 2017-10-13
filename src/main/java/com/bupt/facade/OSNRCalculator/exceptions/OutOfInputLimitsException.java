package com.bupt.facade.OSNRCalculator.exceptions;

import com.bupt.util.exception.controller.input.IllegalArgumentException;

public class OutOfInputLimitsException extends IllegalArgumentException {
    public OutOfInputLimitsException() {
        super("Argument out of limit！.\n");
    }

    public OutOfInputLimitsException(String message) {
        super(message + "\n");
    }

}
