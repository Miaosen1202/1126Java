package com.wdcloud.lms.business.people.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum UserInviteTypeEnum {
    /**
     * 邮箱邀请
     */
    EMAIL(1),
    /**
     * 登录ID邀请
     */
    LOGIN_ID(2),
    /**
     * SIS ID邀请
     */
    SIS_ID(3);

    private Integer type;

    public static UserInviteTypeEnum typeOf(Integer type) {
        for (UserInviteTypeEnum inviteTypeEnum : values()) {
            if (Objects.equals(inviteTypeEnum.type, type)) {
                return inviteTypeEnum;
            }
        }
        return null;
    }
}
