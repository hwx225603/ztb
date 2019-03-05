package com.xiaour.spring.boot.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 表示注解在运行时依然存在
@Target(ElementType.METHOD)  
@Documented
public @interface noNull {
    public String str() default "";
}
