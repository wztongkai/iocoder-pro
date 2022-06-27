package com.iocoder.yudao.module.commons.enums.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 角色标识枚举
 */
@Getter
@AllArgsConstructor
public enum RoleCodeEnum {

    SUPER_ADMIN("super_admin", "超级管理员"),
    ;

    /**
     * 角色编码
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;

    public static boolean isSuperAdmin(String code) {
        return Objects.equals(SUPER_ADMIN.getCode(), code);
    }

}
