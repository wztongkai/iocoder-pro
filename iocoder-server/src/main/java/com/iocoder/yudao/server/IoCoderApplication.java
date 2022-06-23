package com.iocoder.yudao.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wu kai
 * @date 2022/6/20
 */
@SpringBootApplication(scanBasePackages = {"com.iocoder.yudao.framework","com.iocoder.yudao.commons","com.iocoder.yudao.system"})
@MapperScan(value = "com.iocoder.yudao.system.mapper")
public class IoCoderApplication {

    public static void main(String[] args) {
        SpringApplication.run(IoCoderApplication.class, args);
    }
}
