package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRegistryOriginEnum {
    /**
     * 管理员添加
     */
    ADMIN(1),

    /**
     * 邀请加入
     */
    INVITE(2),

    /**
     * 自行注册加入
     */
    SELF_ENROLL(3),
    /**
     * 系统添加（创建课程时会自动添加创建用户到课程）
     */
    SYSTEM(4);

    private Integer origin;
}
