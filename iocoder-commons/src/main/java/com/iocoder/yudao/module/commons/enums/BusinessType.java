package com.iocoder.yudao.module.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型枚举类
 */

@Getter
@AllArgsConstructor
public enum BusinessType {
    /**
     * 查询
     */
    SELECT("SELECT","查询"),
    /**
     * 新增
     */
    INSERT("INSERT","新增"),

    /**
     * 修改
     */
    UPDATE("UPDATE","修改"),

    /**
     * 删除
     */
    DELETE("DELETE","删除"),
    /**
     * 导出
     */
    EXPORT("EXPORT","导出"),
    /**
     * 导入
     */
    IMPORT("IMPORT","导入"),

    /**
     * 其它
     */
    OTHER("OTHER","其他"),

    /**
     * 上传
     */
    UPLOAD("UPLOAD","上传"),

    /**
     * 授权
     */
    GRANT("GRANT","授权");

    /**
     * 枚举编码
     */
    private final String code;
    /**
     * 枚举名
     */
    private final String name;
}
