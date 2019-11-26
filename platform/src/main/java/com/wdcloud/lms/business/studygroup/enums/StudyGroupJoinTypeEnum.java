package com.wdcloud.lms.business.studygroup.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum StudyGroupJoinTypeEnum {
    /**
     * 任意加入
     */
    FREE(1),
    /**
     * 仅限邀请
     */
    ONLY_INVITE(2);

    private Integer type;

    public static StudyGroupJoinTypeEnum typeOf(Integer type) {
        for (StudyGroupJoinTypeEnum joinTypeEnum : values()) {
            if (Objects.equals(joinTypeEnum.type, type)) {
                return joinTypeEnum;
            }
        }

        return null;
    }
}
