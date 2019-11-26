package com.wdcloud.lms.core.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserParticapateOpEnum {
    /**
     * 查看（协作文档)
     */
    VIEW(1),
    /**
     * 创建 wiki page
     */
    CREATE(2),
    /**
     * 编辑(协作文档)
     */
    EDIT(3),
    /**
     * 回复（作业、测验、讨论、公告)
     */
    SUBMIT(4),
    /**
     * 参与（网络会议）
     */
    JOIN(5);

    private Integer op;
}
