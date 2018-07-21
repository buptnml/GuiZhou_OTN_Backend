package com.otn.controller.util;


import com.otn.util.exception.controller.input.ArgumentOutOfLimitsException;
import com.otn.util.exception.controller.input.IllegalArgumentException;
import com.otn.util.exception.controller.input.NullArgumentException;
import com.otn.util.exception.controller.result.NoneGetException;
import com.otn.util.exception.controller.result.NoneRemoveException;
import com.otn.util.exception.controller.result.NoneSaveException;
import com.otn.util.exception.controller.result.NoneUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceException;


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
        logger.error("Request: " + request.getRequestURL() + " raised:", e);
        return e.getMessage();
    }

    @ExceptionHandler(NoneGetException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoneGetException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised:", e);
        return e.getMessage();
    }

    @ExceptionHandler(NoneRemoveException.class)
    @ResponseStatus(HttpStatus.GONE)
    public String handleNoneRemoveException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised:", e);
        return e.getMessage();
    }

    @ExceptionHandler(NoneSaveException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleNoneSaveException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised:", e);
        return e.getMessage();
    }

    @ExceptionHandler(NoneUpdateException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleNoneUpdateException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised:", e);
        return e.getMessage();
    }

    @ExceptionHandler(InterruptedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleInterruptedException(HttpServletRequest request, RuntimeException e) {
        logger.error("Request: " + request.getRequestURL() + " raised:", e);
        return "系统内部发生错误，请稍等再试";
    }


    @ExceptionHandler(ArgumentOutOfLimitsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleArgumentOutOfLimitsException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised:", e);
        return e.getMessage();
    }

    @ExceptionHandler({NullArgumentException.class,})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNullArgumentException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised:", e);
        return e.getMessage();
    }

    @ExceptionHandler({IllegalArgumentException.class, java.lang.IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised:", e);
        return e.getMessage();
    }

    @ExceptionHandler({com.mysql.jdbc.exceptions.jdbc4.CommunicationsException.class, RecoverableDataAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised " + e);
        return "和远程数据库失去连接，请稍后重试";
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMySQLIntegrityConstraintViolationException(HttpServletRequest request, RuntimeException e) {
        logger.warn("Request: " + request.getRequestURL() + " raised:", e);
        return "输入的信息中关键内容和数据库记录重复，请重新输入。";
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public String handleHttpMediaTypeNotSupportedException(HttpServletRequest request, Exception e) {
        logger.error("Request: " + request.getRequestURL() + " raised:", e);
        return "发送的body格式不正确，请检查Headers设置！";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(HttpServletRequest request, Exception e) {
        logger.error("Request: " + request.getRequestURL() + " raised:", e);
        return "输入信息为空或不合法，请检查输入信息重新输入。";
    }

    @ExceptionHandler(WebServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleWebServiceException(HttpServletRequest request, Exception e) {
        logger.error("Request: " + request.getRequestURL() + " raised:", e);
        return "无法访问位于以下位置的 WSDL: http://10.114.223.15/resourceWebService/ResourceServicePort?wsdl";
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
