package com.iocoder.yudao.module.auth.api;

import com.iocoder.yudao.module.framework.config.web.auth.service.LoginAuthService;
import com.iocoder.yudao.module.framework.config.web.auth.vo.AuthLoginReqVO;
import com.iocoder.yudao.module.framework.config.web.auth.vo.AuthLoginRespVO;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * @author wu kai
 * @date 2022/6/23
 */
@Api(tags = "管理后台 - 用户登录")
@RestController
@Validated
public class LoginAuthController {

    @Resource
    LoginAuthService loginAuthService;

    @PostMapping("/login")
    @ApiOperation("使用账号密码登录")
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return success(loginAuthService.login(reqVO));
    }

}
