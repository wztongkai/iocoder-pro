package com.iocoder.yudao.module.framework.config.security.web.service;

import com.iocoder.yudao.module.commons.constant.Constants;
import com.iocoder.yudao.module.commons.core.domain.LoginUser;
import com.iocoder.yudao.module.commons.core.redis.RedisCache;
import com.iocoder.yudao.module.commons.utils.ServletUtils;
import com.iocoder.yudao.module.commons.utils.StringUtils;
import com.iocoder.yudao.module.commons.utils.ip.AddressUtils;
import com.iocoder.yudao.module.commons.utils.ip.IpUtils;
import com.iocoder.yudao.module.commons.utils.uuid.IdUtils;
import com.iocoder.yudao.module.framework.config.security.JwtSecurityProperties;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: kai wu
 * @Date: 2022/6/3 22:35
 */
@Component
public class JwtTokenService {

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    private final JwtSecurityProperties jwtSecurityProperties;

    private final RedisCache redisCache;



    public JwtTokenService(JwtSecurityProperties jwtSecurityProperties, RedisCache redisCache) {
        this.jwtSecurityProperties = jwtSecurityProperties;
        this.redisCache = redisCache;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) throws Exception {
        // 获取请求携带的令牌
        String token = this.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            try {
                Claims claims = this.parseToken(token);
                // 解析对应的权限以及用户信息
                String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
                String userKey = this.getTokenKey(uuid);
                LoginUser sysUser = redisCache.getCacheObject(userKey);
                return sysUser;
            } catch (Exception e) {
                throw new Exception(StringUtils.format("获取用户身份失败", e));
            }
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = this.getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        this.setUserAgent(loginUser);
        this.refreshToken(loginUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return getToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String getToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, this.jwtSecurityProperties.getSecret()).compact();
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            this.refreshToken(loginUser);
        }
    }

    /**
     * 获取请求token
     *
     * @param request request
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(this.jwtSecurityProperties.getHeader());
        if (StringUtils.isNotEmpty(token) && token.startsWith(this.jwtSecurityProperties.getTokenStartWith())) {
            token = token.replace(this.jwtSecurityProperties.getTokenStartWith(), "");
        }
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(this.jwtSecurityProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + this.jwtSecurityProperties.getExpireTime() * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = this.getTokenKey(loginUser.getToken());
        this.redisCache.setCacheObject(userKey, loginUser, this.jwtSecurityProperties.getExpireTime(), TimeUnit.MINUTES);
    }

    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }

    /**
     * 设置用户代理信息
     *
     * @param loginUser 登录信息
     */
    public void setUserAgent(LoginUser loginUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());
    }

}
