package com.xuecheng.base.annotation;

import com.xuecheng.base.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解,用于表示某个方法需要进行功能字段的自动填充
 *
 * @author zhangyu
 * @date 2023/12/12 14:46
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {

    /**
     * 通过枚举类指定数据库操作方式:insert、update
     * @return
     */
    OperationType value();
}
