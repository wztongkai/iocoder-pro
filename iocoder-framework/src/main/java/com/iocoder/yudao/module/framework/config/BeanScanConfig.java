package com.iocoder.yudao.module.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

/**
 * @author wu kai
 * @date 2022/6/23
 */
@SuppressWarnings("SpringComponentScan")
@ComponentScans(value = {
        @ComponentScan(value = "com.iocoder.yudao.module"),
        @ComponentScan(value = "com.iocoder.yudao.server")
})
@Configuration
public class BeanScanConfig {}
