package com.bupt.facade.OSNRCalculator.exceptions;

import com.bupt.util.exception.controller.input.NullArgumentException;

public class LinkNotFoundException extends NullArgumentException {
    public LinkNotFoundException() {
        super("没有找到两点之间的链路！\n");
    }

    public LinkNotFoundException(String node1, String node2) {
        super(node1 + "和" + node2 + "之间的链路不存在或已断开\n");
    }
}
