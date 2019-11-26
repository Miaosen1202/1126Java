package com.wdcloud.lms.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 学生用户活动: 参与网络会议，查看、编辑协作文档，公告、讨论评论，测验提交，作业提交，创建Page
 */
@Getter
@AllArgsConstructor
public enum UserParticipateOpEnum {
    /**
     * 查看
     */
    VIEW(1),

    /**
     * 创建(Page)
     */
    CREATE(2),

    /**
     * 编辑(Page，协作文档)
     */
    EDIT(3),

    /**
     * 提交（作业、测验答案，讨论、公告回复）
     */
    SUBMIT(4),

    /**
     * 参与（网络会议）
     */
    JOIN(5);

    private int op;

    public static UserParticipateOpEnum opOf(Integer op) {
        for (UserParticipateOpEnum opEnum : values()) {
            if (Objects.equals(opEnum.op, op)) {
                return opEnum;
            }
        }
        return null;
    }
}
