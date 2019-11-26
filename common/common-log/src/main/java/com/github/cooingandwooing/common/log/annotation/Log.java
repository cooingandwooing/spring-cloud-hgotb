package com.github.cooingandwooing.common.log.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author gaoxiaofeng
 * @date 2019/3/12 23:50
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 描述
     *
     * @return {String}
     */
    String value();
}
