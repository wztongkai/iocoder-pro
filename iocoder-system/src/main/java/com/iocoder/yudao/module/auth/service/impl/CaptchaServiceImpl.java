package com.iocoder.yudao.module.auth.service.impl;

import com.iocoder.yudao.module.auth.service.CaptchaService;
import com.iocoder.yudao.module.commons.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 验证码接口
 *
 * @author wu kai
 * @date 2022/6/24
 */

@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Value("${iocoder.captcha.enable}")
    private Boolean enable;

    @Resource
    RedisCache redisCache;

    @Override
    public Boolean isCaptchaEnable() {
        return this.enable;
    }

    @Override
    public void deleteCaptchaCode(String uuid) {
        redisCache.deleteObject(uuid);
    }
}
