package com.iocoder.yudao.module.commons.enums.activiti;

import java.util.Objects;

/**
 * @author wu kai
 * @since 2022/9/28
 */
public enum BaseEnum {

    FLOW_START("FLOW_START", "开始"),
    FLOW_CANCEL("FLOW_CANCEL", "取消"),
    FLOW_END("FLOW_END", "结束");

    /**
     * 相应编码
     */
    private String code;
    /**
     * 对应信息
     */
    private String message;

    BaseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Enum get() {
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static BaseEnum match(String code) {
        for (BaseEnum approvalEnum : BaseEnum.values()) {
            if (Objects.equals(approvalEnum.getCode(), code)) {
                return approvalEnum;
            }
        }
        return null;
    }
}
