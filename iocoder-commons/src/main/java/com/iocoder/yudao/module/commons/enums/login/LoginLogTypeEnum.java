package com.iocoder.yudao.module.commons.enums.login;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录日志的类型枚举
 */
@Getter
@AllArgsConstructor
public enum LoginLogTypeEnum {

    LOGIN_USERNAME(100L), // 使用账号登录
    LOGIN_MOBILE(103L), // 使用手机登陆
    LOGIN_SMS(104L), // 使用短信登陆

    LOGOUT_SELF(200L),  // 自己主动登出
    LOGOUT_DELETE(202L), // 强制退出
    ;

    /**
     * 日志类型
     */
    private final Long type;
}
