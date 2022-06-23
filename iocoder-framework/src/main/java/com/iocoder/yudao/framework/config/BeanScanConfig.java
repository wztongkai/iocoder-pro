package com.iocoder.yudao.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

/**
 * @author wu kai
 * @date 2022/6/23
 */
@SuppressWarnings("SpringComponentScan")
@ComponentScans(value = {
        @ComponentScan(value = "com.iocoder.yudao.framework"),
        @ComponentScan(value = "com.iocoder.yudao.commons"),
        @ComponentScan(value = "com.iocoder.yudao.system"),
        @ComponentScan(value = "com.iocoder.yudao.module")
})
@Configuration
public class BeanScanConfig {}
