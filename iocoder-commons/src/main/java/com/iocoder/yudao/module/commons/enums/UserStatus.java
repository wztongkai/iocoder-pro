package com.iocoder.yudao.module.commons.enums;

/**
 * 用户状态
 *
 * @author kai wu
 */
public enum UserStatus {
    OK("0", "正常"), DISABLE("1", "停用");

    private final String code;
    private final String info;

    UserStatus(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return this.code;
    }

    public String getInfo() {
        return this.info;
    }
}
