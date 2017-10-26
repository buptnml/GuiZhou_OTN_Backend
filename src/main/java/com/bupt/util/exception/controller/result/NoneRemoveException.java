package com.bupt.util.exception.controller.result;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 删除失败异常
 */
public class NoneRemoveException extends RequestResultErrorException {
    public NoneRemoveException() {
        super("要删除的信息和记录不匹配！\n");
    }

    public NoneRemoveException(String message) {
        super(message + "\n");
    }
}
