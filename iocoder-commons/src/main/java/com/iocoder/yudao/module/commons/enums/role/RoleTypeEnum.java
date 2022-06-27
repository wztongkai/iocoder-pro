package com.iocoder.yudao.module.commons.enums.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleTypeEnum {

    /**
     * 系统内置角色
     */
    SYSTEM(1),
    /**
     * 用户自定义角色
     */
    CUSTOM(2);

    private final Integer type;

}
