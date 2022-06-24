package com.iocoder.yudao.module.auth.api;

import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.framework.config.web.auth.service.CaptchaService;
import com.iocoder.yudao.module.framework.config.web.auth.vo.CaptchaImageRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * @author wu kai
 * @date 2022/6/24
 */
@Api(tags = "管理后台 - 获取验证码")
@RestController
public class CaptchaController {
    @Resource
    private CaptchaService captchaService;

    @GetMapping("/captchaImage")
    @ApiOperation("生成图片验证码")
    public CommonResult<CaptchaImageRespVO> getCaptchaImage() {
        return success(captchaService.getCaptchaImage());
    }
}
