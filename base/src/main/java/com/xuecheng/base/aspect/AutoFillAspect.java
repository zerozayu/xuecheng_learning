package com.xuecheng.base.aspect;

import com.xuecheng.base.annotation.AutoFill;
import com.xuecheng.base.constant.AutoFillMethods;
import com.xuecheng.base.enumeration.OperationType;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义切面,实现公共字段自动填充逻辑
 *
 * @author zhangyu
 * @date 2023/12/12 14:53
 */
@Slf4j
@Component
@Aspect
public class AutoFillAspect {

    /**
     * 定义切入点
     * 1. '*' 表示任何返回类型
     * 2.
     * - com.xuecheng..mapper.* 表示com.xuecheng包及其任意深度子包下的任意类
     * - .* 表示该类的任意方法
     * - (..) 表示任何参数列表
     */
    @Pointcut("execution(* com.xuecheng..mapper.*.*(..)) && @annotation(com.xuecheng.base.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    /**
     * 定义前置通知,在通知中进行公共字段的赋值
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段的自动填充...");

        /**
         * 通知内容
         */
        // 1. 获取到当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = autoFill.value();

        // 2. 获取到当前被拦截的方法的参数,也就是实体对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }

        Object entity = args[0];

        // 3. 准备赋值的数据
        String id = String.valueOf(System.currentTimeMillis());

        // 4. 根据当前不同的操作类型,为对应的属性通过反射来赋值
        if (operationType == OperationType.INSERT) {
            Method setId = ReflectUtils.getAccessibleMethod(entity, AutoFillMethods.SET_ID);
            try {
                setId.invoke(entity, id);
            } catch (IllegalAccessException | InvocationTargetException e) {
                XueChengPlusException.cast("公共字段的填充时异常: " + e.getMessage());
            }
        } else if (operationType == OperationType.UPDATE) {
            // todo (zhangyu, 2023-12-12, 23:08:14) : 更新的时候的操作
        }
    }
}
