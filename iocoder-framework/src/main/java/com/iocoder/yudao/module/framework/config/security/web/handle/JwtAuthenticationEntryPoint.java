package com.iocoder.yudao.module.framework.config.security.web.handle;

import com.alibaba.fastjson2.JSON;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.utils.ServletUtils;
import com.iocoder.yudao.module.commons.utils.StringUtils;
import com.iocoder.yudao.module.commons.utils.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 用户认证逻辑
 *
 * @Author: kai wu
 * @Date: 2022/6/3 22:15
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", httpServletRequest.getRequestURI());
        ServletUtils.renderString(httpServletResponse, JSON.toJSONString(CommonResult.error(code, msg)));
    }
}
