package com.xuecheng.base.enumeration;

import lombok.Data;
import lombok.Getter;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * 通用错误信息
 *
 * @author zhangyu
 * @date 2023/11/17 17:33
 */
@Getter
public enum CommonError {
    OK("000000", "一切 ok"),
    UNKNOWN_ERROR("999999", "执行过程异常，请重试。"),
    PARAMS_ERROR("900002", "非法参数"),
    OBJECT_NULL("900003", "对象为空"),
    QUERY_NULL("900004", "查询结果为空"),
    REQUEST_NULL("900005", "请求参数为空");


    private final String errCode;

    private final String errMessage;

    CommonError(String errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public String getCode() {
        return errCode;
    }
}
