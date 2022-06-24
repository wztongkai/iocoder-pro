package com.iocoder.yudao.module.auth.service;

/**
 * 验证码接口
 */
public interface CaptchaService {

    /**
     * 是否开启图片验证码
     *
     * @return 是否
     */
    Boolean isCaptchaEnable();

    /**
     * 删除 uuid 对应的验证码
     *
     * @param uuid 验证码编号
     */
    void deleteCaptchaCode(String uuid);
}
