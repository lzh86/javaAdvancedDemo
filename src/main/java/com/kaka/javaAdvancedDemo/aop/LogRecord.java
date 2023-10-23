package com.kaka.javaAdvancedDemo.aop;


import java.lang.annotation.*;

@Target({ElementType.METHOD}) //注解作用于方法级别
@Retention(RetentionPolicy.RUNTIME) //运行时起作用
//@Inherited
//@Documented
public @interface LogRecord {

    String name() default "";

    String el();
}
