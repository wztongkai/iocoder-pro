package com.iocoder.yudao.module.framework.config.web.auth.service;

import com.iocoder.yudao.module.framework.config.web.auth.vo.AuthLoginReqVO;
import com.iocoder.yudao.module.framework.config.web.auth.vo.AuthLoginRespVO;

import javax.validation.Valid;

public interface LoginAuthService {

    /**
     * 账号登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AuthLoginRespVO login(@Valid AuthLoginReqVO reqVO);
}
