package com.hlwcbz.common.annotation;

import java.lang.annotation.*;

/**
 * 字典转换注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DicType {
    String value();
}
