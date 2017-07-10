package com.bupt.util.exception.controller.result;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 删除失败异常
 */
public class NoneRemoveException extends RequestResultErrorException {
    public NoneRemoveException() {
        super("Nothing removed from the database.\n");
    }


}
