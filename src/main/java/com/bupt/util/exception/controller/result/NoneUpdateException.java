package com.bupt.util.exception.controller.result;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 更新失败异常
 */
public class NoneUpdateException extends RequestResultErrorException {
    public NoneUpdateException() {
        super("Nothing updated from the database.\n");
    }

}
