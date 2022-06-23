package com.iocoder.yudao.module.framework.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * jwt 配置类
 *
 * @Author: kai wu
 * @Date: 2022/6/3 22:08
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "token")
public class JwtSecurityProperties {
    /**
     * 令牌自定义标识
     */
    private String header;

    /**
     * 令牌前缀
     */
    private String tokenStartWith;

    /**
     * 令牌秘钥
     */
    private String secret;

    /**
     * 令牌有效期（默认30分钟）
     */
    private int expireTime;

    /**
     * 返回令牌前缀
     */
    public String getTokenStartWith() {
        return this.tokenStartWith + " ";
    }
}
