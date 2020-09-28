package com.lin.rabbit.annotation;

import com.lin.rabbit.enums.RabbitEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息队列注解，必须用唯一的枚举
 * @Author: linFeng
 * @Date: 2020/9/28 16:04
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RabbitAnnotation {
    RabbitEnum value();
}
