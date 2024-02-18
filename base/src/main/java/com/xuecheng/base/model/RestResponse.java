package com.xuecheng.base.model;

import com.xuecheng.base.enumeration.CommonError;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 响应实体类
 *
 * @author zhangyu
 * @date 2024/1/29 22:01
 */
@Data
@Accessors(chain = true)
public class RestResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String MSG_SUCCESS = "操作成功";
    public static final String MSG_ERROR = "操作失败";

    /**
     * Http状态响应码
     */
    private Integer code;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 数据
     */
    private T result;

    public static <T> RestResponse<T> success(String message) {
        return success(message, null);
    }

    public static <T> RestResponse<T> success(T result) {
        return success(MSG_SUCCESS, result);
    }

    public static <T> RestResponse<T> success(String message, T result) {
        return success(CommonError.OK.getCode(), message, result);
    }

    public static <T> RestResponse<T> success(String errorCode, String message, T result) {
        return success(0, errorCode, message, result);
    }

    public static <T> RestResponse<T> success(Integer code, String errorCode, String message, T result) {
        return new RestResponse<T>()
                .setCode(code)
                .setErrorCode(errorCode)
                .setMessage(message)
                .setResult(result);
    }

    public static <T> RestResponse<T> validfail(String message) {
        return validfail(message, null);
    }

    public static <T> RestResponse<T> validfail(T data) {
        return validfail(MSG_SUCCESS, data);
    }

    public static <T> RestResponse<T> validfail(String message, T result) {
        return validfail(CommonError.UNKNOWN_ERROR.getCode(), message, result);
    }

    public static <T> RestResponse<T> validfail(String errorCode, String message, T result) {
        return validfail(1, errorCode, message, result);
    }

    public static <T> RestResponse<T> validfail(Integer code, String errorCode, String message, T result) {
        return new RestResponse<T>()
                .setCode(code)
                .setErrorCode(errorCode)
                .setMessage(message)
                .setResult(result);
    }
}
