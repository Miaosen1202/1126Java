package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum OriginTypeEnum {
    /**
     * 作业
     */
    ASSIGNMENT(1),
    /**
     * 讨论
     */
    DISCUSSION(2),
    /**
     * 测验
     */
    QUIZ(3),
    /**
     * 文件
     */
    FILE(4),
    /**
     * 页面
     */
    PAGE(5),
    /**
     * 公告
     */
    ANNOUNCE(6),

    /**
     * 讨论回复
     */
    DISCUSSION_REPLY(7),

    /**
     * 公告回复
     */
    ANNOUNCE_REPLY(8),

    /**
     * 评分评论
     */
    GRADE_COMMENT(9),

    /**
     * 协作文档
     */
    DOCUMENT(10),

    /**
     * 网络会议
     */
    MEETING(11),

    /**
     * 直播
     */
    LIVE(12),

    /**
     * 文本标题
     */
    TEXT_HEADER(13),

    /**
     * 外部链接
     */
    EXTERNAL_URL(14),

    /**
     * 课程
     */
    COURSE(15),

    /**
     * 测验问题
     */
    QUIZ_QUESTION(16),

    /**
     * 测验问题
     */
    QUIZ_QUESTION_OPTION(17);


    private Integer type;

    public static OriginTypeEnum typeOf(Integer type) {
        for (OriginTypeEnum value : values()) {
            if (Objects.equals(value.type, type)) {
                return value;
            }
        }
        return null;
    }
}
