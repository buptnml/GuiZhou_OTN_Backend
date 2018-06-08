package com.otn.facade.OSNRCalculator.exceptions;

import com.otn.util.exception.controller.input.NullArgumentException;

public class NetElementNotFoundException extends NullArgumentException {
    public NetElementNotFoundException() {
        super("没有找到网元！\n");
    }

    public NetElementNotFoundException(String message) {
        super("网元" + message + "不存在\n");
    }
}
