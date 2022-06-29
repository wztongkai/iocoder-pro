package com.iocoder.yudao.module.commons.enums.common;

import lombok.Getter;

@Getter
public enum ResultsEnum {

    SUCCEED(1, 200, "成功"),
    FAILURE(2, -1,"失败");

    private int result;

    private int code;

    private String name;

    ResultsEnum(int result, int code, String name) {
        this.result = result;
        this.code = code;
        this.name = name;
    }
}
