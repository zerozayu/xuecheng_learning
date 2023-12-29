package com.xuecheng.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 错误响应参数包装
 *
 * @author zhangyu
 * @date 2023/11/19 22:37
 */
@Data
@AllArgsConstructor
public class RestErrorResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String errCode;

    private String errMessage;
}
