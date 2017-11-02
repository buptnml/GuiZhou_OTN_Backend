package com.bupt.controller.utils;


import com.bupt.util.exception.controller.input.ArgumentOutOfLimitsException;
import com.bupt.util.exception.controller.input.IllegalArgumentException;
import com.bupt.util.exception.controller.input.NullArgumentException;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by 韩宪斌 on 2017/6/29.
 * Controller层异常处理类，负责：
 * 处理所有Controller层上的异常
 * 返回合理的状态码
 * 在后台log中记录抛出的异常信息
 */
@ControllerAdvice
@RestController
@ApiIgnore
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ExceptionHandlerController {
    private Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleHttpMessageNotReadableException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised " + e);
        return e.getMessage();
    }

    @ExceptionHandler(NoneGetException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoneGetException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised " + e);
        return e.getMessage();
    }

    @ExceptionHandler(NoneRemoveException.class)
    @ResponseStatus(HttpStatus.GONE)
    public String handleNoneRemoveException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised " + e);
        return e.getMessage();
    }

    @ExceptionHandler(NoneSaveException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleNoneSaveException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised " + e);
        return e.getMessage();
    }

    @ExceptionHandler(NoneUpdateException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleNoneUpdateException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised " + e);
        return e.getMessage();
    }

    @ExceptionHandler(ArgumentOutOfLimitsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleArgumentOutOfLimitsException(HttpServletRequest request, RuntimeException e) {
        logger.error("Request: " + request.getRequestURL() + " raised " + e);
        return e.getMessage();
    }

    @ExceptionHandler({NullArgumentException.class,})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNullArgumentException(HttpServletRequest request, RuntimeException e) {
        logger.error("Request: " + request.getRequestURL() + " raised " + e);
        return e.getMessage();
    }

    @ExceptionHandler({IllegalArgumentException.class, java.lang.IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(HttpServletRequest request, RuntimeException e) {
        logger.error("Request: " + request.getRequestURL() + " raised " + e);
        return e.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised " + e);
        return "输入信息为空或不合法，请检查输入信息重新输入。";
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMySQLIntegrityConstraintViolationException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised " + e);
        return "输入的信息中关键内容和数据库记录重复，请重新输入。";
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(HttpServletRequest request, Exception e) {
        logger.error("Request: " + request.getRequestURL() + " raised " + e);
        return "输入信息为空或不合法，请检查输入信息重新输入！";
    }


    @RequestMapping(value = "/error_500")
    public ResponseEntity<String> error_500() {
        return new ResponseEntity<>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/error_404")
    public ResponseEntity<String> error_404() {
        return new ResponseEntity<>("REQUEST_URL_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/error_405")
    public ResponseEntity<String> error_405() {
        return new ResponseEntity<>("METHOD_NOT_ALLOWED", HttpStatus.METHOD_NOT_ALLOWED);
    }

}
