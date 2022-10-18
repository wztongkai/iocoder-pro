package com.iocoder.yudao.module.framework.config.security;


import com.iocoder.yudao.module.framework.config.properties.PermitAllUrlProperties;
import com.iocoder.yudao.module.framework.config.security.filter.JwtAuthenticationTokenFilter;
import com.iocoder.yudao.module.framework.config.security.web.handle.JwtAuthenticationEntryPoint;
import com.iocoder.yudao.module.framework.config.security.web.handle.LogoutSuccessHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

/**
 * @Author: kai wu
 * @Date: 2022/6/4 00:25
 */
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义用户认证逻辑
     */
    @Resource
    private UserDetailsService userDetailsService;
    /**
     * 认证失败处理类
     */
    @Resource
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    /**
     * token认证过滤器
     */
    @Resource
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 退出处理类
     */
    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * 跨域过滤器
     */
    @Resource
    private CorsFilter corsFilter;

    /**
     * 允许匿名访问的地址
     */
    @Resource
    private PermitAllUrlProperties permitAllUrl;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 注解标记允许匿名访问的url
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();
        this.permitAllUrl.getUrls().forEach(url -> registry.antMatchers(url).permitAll());

        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                .antMatchers("/login", "/register", "/captchaImage").anonymous()
                // 静态资源，可匿名访问
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**").permitAll()
                .antMatchers("/swagger-ui.html", "/doc.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs",
                        "/druid/**", "/**/**/*.doc").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        // 添加Logout filter
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(this.logoutSuccessHandler);
        // 添加JWT filter
        httpSecurity.addFilterBefore(this.authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(this.corsFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(this.corsFilter, LogoutFilter.class);
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.bCryptPasswordEncoder());
    }


}
