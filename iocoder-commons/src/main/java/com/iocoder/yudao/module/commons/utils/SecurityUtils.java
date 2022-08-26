package com.iocoder.yudao.module.commons.utils;


import com.iocoder.yudao.module.commons.core.domain.DeptVo;
import com.iocoder.yudao.module.commons.core.domain.LoginUser;
import com.iocoder.yudao.module.commons.exception.ServiceException;
import com.iocoder.yudao.module.commons.utils.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Objects;

/**
 * @Author: kai wu
 * @Date: 2022/6/3 23:32
 */
public class SecurityUtils {
    /**
     * 用户ID
     **/
    public static Long getUserId() {
        try {
            return getLoginUser().getUserId();
        } catch (Exception e) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "获取用户ID异常");
        }
    }

    /**
     * 获取登陆用户id
     *
     * @return 用户ID
     */
    public static Long getLoginUserId() {
        if (!Objects.equals(getAuthentication().getPrincipal(), "anonymousUser")) {
            LoginUser loginUser = (LoginUser) getAuthentication().getPrincipal();
            return loginUser.getUserId();
        }
        return null;
    }

    /**
     * 获取部门编号数组
     **/
    public static List<DeptVo> getDeptId() {
        try {
            return getLoginUser().getDeptVoList();
        } catch (Exception e) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "获取部门ID异常");
        }
    }

    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            return getLoginUser().getUsername();
        } catch (Exception e) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "获取用户账户异常");
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "获取用户信息异常");
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
