package com.iocoder.yudao.module.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wu kai
 * @since 2022/10/20
 */

@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    HTML(1, "html"),
    WORD(2, "word"),
    MD(3, "md"),
    PDF(4, "pdf");

    /**
     * 状态值
     */
    private final Integer code;
    /**
     * 状态名
     */
    private final String name;
}
