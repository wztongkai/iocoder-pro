package com.iocoder.yudao.module.commons.enums.db;

/**
 * @author wu kai
 * @since 2022/9/6
 */

public enum DBMenuType {
    DB_FILE_ANNEX("DB_ANNEX", "数据库附件"),
    USER_INFO_ANNEX("USER_INFO-ANNEX", "数据库附件");

    private final String code;
    private final String info;

    DBMenuType(String code, String info) {
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
