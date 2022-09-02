package com.iocoder.yudao.module.framework.config.web.auth.service;

import com.iocoder.yudao.module.system.vo.auth.AuthLoginReqVO;
import com.iocoder.yudao.module.system.vo.auth.AuthLoginRespVO;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

public interface LoginAuthService {

    /**
     * 账号登录
     *
     * @param reqVO 登录信息
     * @return 登录结果
     */
    AuthLoginRespVO login(@Valid AuthLoginReqVO reqVO);

    /**
     * 退出登录
     * @param token token信息
     * @param request 请求信息
     * @param type 登录类型
     */
    void logout(String token, HttpServletRequest request, Long type) throws Exception;
}
