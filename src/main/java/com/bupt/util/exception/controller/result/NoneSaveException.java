package com.bupt.util.exception.controller.result;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 插入失败异常
 */
public class NoneSaveException extends RequestResultErrorException {
    public NoneSaveException() {
        super("Nothing saved from the database.\n");
    }

}
