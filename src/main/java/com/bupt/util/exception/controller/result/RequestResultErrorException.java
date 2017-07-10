package com.bupt.util.exception.controller.result;


import com.bupt.util.exception.controller.ControllerException;

/**
 * Created by 韩宪斌 on 2017/6/30.
 * 请求结果异常
 */
public class RequestResultErrorException extends ControllerException {
    public RequestResultErrorException() {
        super();
    }

    public RequestResultErrorException(String message) {
        super(message);
    }
}
