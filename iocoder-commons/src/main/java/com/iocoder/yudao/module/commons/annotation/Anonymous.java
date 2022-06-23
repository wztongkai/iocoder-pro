package com.iocoder.yudao.module.commons.annotation;

import java.lang.annotation.*;


/**
 * 匿名访问不鉴权注解
 *
 * @author wu kai
 * @date 2022/6/21
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous {
}
