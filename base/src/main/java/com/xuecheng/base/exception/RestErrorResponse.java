package com.xuecheng.base.exception;

import java.io.Serializable;

/**
 * 错误响应参数包装
 *
 * @author zhangyu
 * @date 2023/11/19 22:37
 */
public class RestErrorResponse implements Serializable {
    private final static Long serialVersionUID = 1L;

    private String errMessage;

    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
