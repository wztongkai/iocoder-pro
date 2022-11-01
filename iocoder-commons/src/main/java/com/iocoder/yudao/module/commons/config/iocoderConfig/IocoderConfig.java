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

//    public static String getName() {
//        return name;
//    }
//
//    public static Boolean getIsEncrypt() {
//        return isEncrypt;
//    }
//
//    public void setIsEncrypt(Boolean isEncrypt) {
//        IocoderConfig.isEncrypt = isEncrypt;
//    }
//
//    public void setName(String name) {
//        IocoderConfig.name = name;
//    }
//
//    public static String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        IocoderConfig.version = version;
//    }
//
//    public static String getCopyrightYear() {
//        return copyrightYear;
//    }
//
//    public void setCopyrightYear(String copyrightYear) {
//        IocoderConfig.copyrightYear = copyrightYear;
//    }
//
//    public static String getProfile() {
//        return profile;
//    }
//
//    public void setProfile(String profile) {
//        IocoderConfig.profile = profile;
//    }
//
//    public static boolean isAddressEnabled() {
//        return addressEnabled;
//    }
//
//    public void setAddressEnabled(boolean addressEnabled) {
//        IocoderConfig.addressEnabled = addressEnabled;
//    }
//
//    /**
//     * 获取导入上传路径
//     */
//    public static String getImportPath() {
//        return getProfile() + "/import";
//    }
//
//    /**
//     * 获取头像上传路径
//     */
//    public static String getAvatarPath() {
//        return getProfile() + "/avatar";
//    }
//
//    /**
//     * 获取下载路径
//     */
//    public static String getDownloadPath() {
//        return getProfile() + "/download/";
//    }
//
//    /**
//     * 获取上传路径
//     */
//    public static String getUploadPath() {
//        return getProfile() + "/upload";
//    }
//
//    public static String getCaptchaType() {
//        return captchaType;
//    }
//
//    public void setCaptchaType(String captchaType) {
//        IocoderConfig.captchaType = captchaType;
//    }
}
