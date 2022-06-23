package com.iocoder.yudao.module.framework.config.security.filter;


import com.iocoder.yudao.module.commons.core.domain.LoginUser;
import com.iocoder.yudao.module.commons.utils.SecurityUtils;
import com.iocoder.yudao.module.commons.utils.ServletUtils;
import com.iocoder.yudao.module.commons.utils.StringUtils;
import com.iocoder.yudao.module.framework.config.security.web.service.JwtTokenService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 *
 * @author kai wu
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        LoginUser loginUser = jwtTokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityUtils.getAuthentication())) {
            // 验证令牌有效期
            jwtTokenService.verifyToken(loginUser);
            // 将用户信息设置到上下文中，方便获取登陆用户的信息
            ServletUtils.setLoginUser(loginUser, request);
        }
        chain.doFilter(request, response);
    }
}
