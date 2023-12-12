package com.xuecheng.base.utils;

import org.apache.commons.lang3.Validate;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 反射工具类
 *
 * @author zhangyu
 * @date 2023/12/12 15:52
 */
public class ReflectUtils {

    public static Method getAccessibleMethod(final Object obj, final String methodName, final Class<?>... parameterTypes) {
        if (obj == null) {
            return null;
        }
        Validate.notBlank(methodName, "methodName can't be blank");

        Method declaredMethod = null;
        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
            try {
                declaredMethod = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(declaredMethod, obj);
            } catch (NoSuchMethodException e) {
                continue;
            }
        }
        return declaredMethod;
    }

    /**
     * 改变private/protected的成员变量为public,尽量不调用实际改动的语句,避免jdk的SecurityManager
     *
     * @param method 方法
     * @param obj    实例对象
     */
    private static void makeAccessible(Method method, Object obj) {
        if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.canAccess(obj)) {
            method.setAccessible(true);
        }
    }

}
