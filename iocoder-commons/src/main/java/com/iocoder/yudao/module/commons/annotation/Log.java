package com.iocoder.yudao.module.commons.annotation;

import com.iocoder.yudao.module.commons.enums.BusinessType;

import java.lang.annotation.*;

/**
 * PARAMETER 用于描述参数
 * METHOD    用于描述方法
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 标题
     */
    String title() default "";

    /**
     * 操作类型
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    boolean isSaveResponseData() default true;
}
