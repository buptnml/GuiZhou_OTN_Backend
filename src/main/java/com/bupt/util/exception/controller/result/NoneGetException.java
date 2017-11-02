package com.bupt.util.exception.controller.result;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 查询失败异常
 */
public class NoneGetException extends RequestResultErrorException {
    public NoneGetException() {
        super("没有从数据库中查询到相关记录！\n");
    }

    public NoneGetException(String message) {
        super(message);
    }
}
