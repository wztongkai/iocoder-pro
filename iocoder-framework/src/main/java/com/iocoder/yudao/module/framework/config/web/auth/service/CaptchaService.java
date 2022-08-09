package com.iocoder.yudao.module.framework.config.web.auth.service;

import com.iocoder.yudao.module.system.vo.auth.CaptchaImageRespVO;

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

    /**
     * 获得验证码图片
     *
     * @return 验证码图片
     */
    CaptchaImageRespVO getCaptchaImage();

}
