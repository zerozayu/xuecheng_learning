package com.xuecheng.base.exception;

import com.xuecheng.base.enumeration.CommonError;
import com.xuecheng.base.model.RestErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author zhangyu
 * @date 2023/11/19 22:54
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 注解@Validated校验引发的异常MethodArgumentNotValidException
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        List<String> msgList = bindingResult.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        String msg = StringUtils.join(msgList, ",");

        log.error("【系统异常】{} - {}", CommonError.PARAMS_ERROR.getErrCode(), msg);
        return new RestErrorResponse(CommonError.PARAMS_ERROR.getErrCode(), msg);
    }

    /**
     * 自定义异常XueChengPlusException
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(XueChengPlusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(XueChengPlusException e) {
        log.error("【系统异常】{} - {}", e.getErrCode(), e.getErrMessage(), e);
        return new RestErrorResponse(e.getErrCode(), e.getErrMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse exception(Exception e) {
        log.error("【系统异常】{}", e.getMessage(), e);
        return new RestErrorResponse(CommonError.UNKNOWN_ERROR.getErrCode(), CommonError.UNKNOWN_ERROR.getErrMessage());
    }
}
