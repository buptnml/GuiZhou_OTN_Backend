package com.facade.OSNRCalculator.exceptions;

import com.util.exception.controller.input.NullArgumentException;

public class DiskNotFoundException extends NullArgumentException {
    public DiskNotFoundException() {
        super("没有找到机盘！\n");
    }

    public DiskNotFoundException(String message) {
        super(message + "下没有机盘");
    }
}
