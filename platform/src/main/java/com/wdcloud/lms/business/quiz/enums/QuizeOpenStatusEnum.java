package com.wdcloud.lms.business.quiz.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 测验开放状态，（学生是否能答题的状态）
 *
 * @author 黄建林
 */
@Getter
@AllArgsConstructor
public enum QuizeOpenStatusEnum {

    /**
     * 允许答题
     */
    PERMIT(1),

    /**
     * 测试处于被教师锁定状态，不能查看
     */
    LOCKED(2),

    /**
     * 表示访问码错误
     */
    ACCESS_CODE_ERROR(3),
    /**
     * 答题次数已经达到限制
     */
    MAXIMUM_NUMBER_ATTAINED(4),

    /**
     * IP地址不在设定范围内
     */
    IP_ERROR(5),


    /**
     * 模块是否解锁逻辑
     */MODULES_LOCKED(6),

    /**
     * 当前时间还没达到规定的开始时间
     */
    QUIZ_NOTBEGIN(7),
    /**
     * 当前时间已经超过了结束时间
     */
    QUIZ_EXCEEDEDENDTIME(8),
    /**
     * 恢复答题
     */
    QUIZ_RECOVERY_RECORD(9);

    private Integer status;

    public static QuizeOpenStatusEnum typeOf(Integer status) {
        for (QuizeOpenStatusEnum value : values()) {
            if (Objects.equals(value.status, status)) {
                return value;
            }
        }

        return PERMIT;
    }
}
