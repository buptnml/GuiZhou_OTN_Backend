package com.bupt.util.exception.controller.input;

/**
 * Created by 韩宪斌 on 2017/6/30.
 */
public class NullArgumentException extends IllegalArgumentException {
    public NullArgumentException() {
        super("The argument is missing or NULL\n");
    }

    public NullArgumentException(String message) {
        super("The argument "+ message + " is missing or NULL.\n");
}
}
