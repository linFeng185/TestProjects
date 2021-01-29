package com.lin.annotation;

import com.lin.enums.ExcelType;

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

    /**
     * 列名
     */
    String name();

    /**
     * 日期格式
     */
    String dateFormat() default "";

    /**
     * 列高
     */
    int height() default 10;

    /**
     * 列宽
     */
    int width() default 20;

    /**
     * 当值为空时的默认值
     */
    String defaultValue() default "";

    /**
     * 类型：IS_EXPORT导入，IS_IMPORT导出，ALL导入导出
     */
    ExcelType excelType() default ExcelType.ALL;

    /**
     * 是否可选字段，如果为true时，会根据请求参数来判断该字段是否显示
     */
    boolean isOptional() default false;

    /**
     * 当isOptional属性为true时，会根据这个字段值来判断该列是否导出
     */
    String judgeStr() default "";

    /**
     * 值转换，将某些特定值转换为另一个值，如：0:女,1:男,2:未知
     */
    String valueConvert() default "";

    /**
     * 下拉选择框内容
     */
    String [] selectBox() default {};

    /**
     * 前缀
     */
    String prefix() default "";

    /**
     * 后缀
     */
    String suffix() default "";

    /**
     * 是否只读
     */
    boolean isReadOnly() default false;
}
