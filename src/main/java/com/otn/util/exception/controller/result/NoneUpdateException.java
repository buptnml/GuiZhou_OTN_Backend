package com.otn.util.exception.controller.result;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 更新失败异常
 */
public class NoneUpdateException extends RequestResultErrorException {
    public NoneUpdateException() {
        super("数据库更新失败！请检查输入信息！\n");
    }

}
