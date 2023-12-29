package com.xuecheng.base.exception;

import com.xuecheng.base.enumeration.CommonError;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.crypto.Cipher;

/**
 * 学成在线项目异常类
 *
 * @author zhangyu
 * @date 2023/11/17 17:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class XueChengPlusException extends RuntimeException {

    private String errCode;

    private String errMessage;

    public XueChengPlusException() {
        super();
    }

    public XueChengPlusException(String errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public static void cast(CommonError commonError) {
        throw new XueChengPlusException(commonError.getErrCode(), commonError.getErrMessage());
    }

    public static void cast(String errCode, String errMessage) {
        throw new XueChengPlusException(errCode, errMessage);
    }

    public static void cast(String errMessage) {
        cast(CommonError.UNKNOWN_ERROR.getErrCode(), errMessage);
    }
}
