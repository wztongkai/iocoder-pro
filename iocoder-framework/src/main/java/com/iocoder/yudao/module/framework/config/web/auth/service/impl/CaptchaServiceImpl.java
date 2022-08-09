package com.iocoder.yudao.module.framework.config.web.auth.service.impl;

import cn.hutool.core.codec.Base64;
import com.google.code.kaptcha.Producer;
import com.iocoder.yudao.module.commons.config.iocoderConfig.IocoderConfig;
import com.iocoder.yudao.module.commons.constant.Constants;
import com.iocoder.yudao.module.commons.core.redis.RedisCache;
import com.iocoder.yudao.module.commons.utils.uuid.IdUtils;
import com.iocoder.yudao.module.framework.config.web.auth.service.CaptchaService;
import com.iocoder.yudao.module.system.vo.auth.CaptchaImageRespVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

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

    @Override
    public CaptchaImageRespVO getCaptchaImage() {

        if (!Boolean.TRUE.equals(this.enable)) {
            return CaptchaImageRespVO.builder().enable(this.enable).build();
        }
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = IocoderConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = this.captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = this.captchaProducer.createText();
            image = this.captchaProducer.createImage(capStr);
        }

        this.redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            assert image != null;
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return CaptchaImageRespVO.builder().enable(this.enable).build();
        }
        return CaptchaImageRespVO.builder().uuid(uuid).img(Base64.encode(os.toByteArray())).enable(this.enable).build();
    }
}
