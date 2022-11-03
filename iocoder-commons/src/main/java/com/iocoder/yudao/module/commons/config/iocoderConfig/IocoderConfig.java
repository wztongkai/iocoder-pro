package com.iocoder.yudao.module.commons.config.iocoderConfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 系统配置类
 *
 * @Author: kai wu
 * @Date: 2022/6/3 16:27
 */
@Data
@Component
@ConfigurationProperties(prefix = "iocoder")
public class IocoderConfig {
    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;

    /**
     * 上传路径
     */
    private String profile;

    /**
     * 获取地址开关
     */
    private Boolean addressEnabled;

    /**
     * 验证码类型
     */
    private String captchaType;

    /**
     * 是否加密 true表示不加密
     */
    private Boolean isEncrypt;

    /**
     * 加密秘钥
     */
    private String aesDataKey;

    /**
     * 在线编辑插件地址
     */
    private String onlinePlugin;

    /**
     * 签章插件地址
     */
    private String singPlugin;

}
