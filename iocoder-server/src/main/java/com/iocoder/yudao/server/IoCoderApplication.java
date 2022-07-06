package com.iocoder.yudao.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author wu kai
 * @date 2022/6/20
 */
@SuppressWarnings("SpringComponentScan")
@SpringBootApplication(scanBasePackages={"com.iocoder.yudao"})
@MapperScan(value = "com.iocoder.yudao.module.system.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class IoCoderApplication {

    public static void main(String[] args) {
        SpringApplication.run(IoCoderApplication.class, args);
    }
}
