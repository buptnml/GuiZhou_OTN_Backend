package com.bupt.util.exception.controller.result;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 查询失败异常
 */
public class NoneGetException extends RequestResultErrorException {
    public NoneGetException() {
        super("Nothing found from the database.\n");
    }

}
