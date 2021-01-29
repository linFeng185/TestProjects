package com.lin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Target: 作用目标：字段
 * Retention: 生命周期为：一直存在
 * @Author: lin
 * @Date: 2021/1/29 17:12
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {
}
