package com.tw.annotation;

import java.lang.annotation.*;

/**
 * 日志记录
 * 记录消息
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Operation {
    String name();
}
