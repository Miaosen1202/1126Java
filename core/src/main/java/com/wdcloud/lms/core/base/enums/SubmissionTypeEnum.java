package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum SubmissionTypeEnum {
    /**
     * 在线
     */
    ONLINE(1),

    /**
     * 书面
     */
    PAPER(2),

    /**
     * 外部工具
     */
    EXT_TOOL(3),

    /**
     * 不提交
     */
    NOT_SUBMIT(4);

    private Integer type;

    public static SubmissionTypeEnum typeOf(Integer type) {
        for (SubmissionTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}
