package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileUsageEnum {
    OTHER(0, ""),

    /**
     * 课程内容相关附件，用户讨论、公告回复附件，小组内容相关附件
     * MyFiles下的unfiled保存了电子学档中的上传文件
     */
    UNFILED(1, "unfiled"),
    /**
     * 存放用户头像文件
     */
    PROFILE(2, "profile pictures"),

    /**
     * 存放用户作业回答附件
     */
    SUBMISSION(3, "Submissions");

    private int usage;
    private String defName;
}
