package com.iocoder.yudao.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用状态枚举
 *
 * @author wu kai
 * @date 2022/6/21
 */
@Getter
@AllArgsConstructor
public enum CommonStatusEnum {

    ENABLE(0, "开启"),
    DISABLE(1, "关闭");

    /**
     * 状态值
     */
    private final Integer status;
    /**
     * 状态名
     */
    private final String name;
}
