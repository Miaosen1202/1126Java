package com.wdcloud.lms.base.dto;

import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class AssignmentGroupItemDTO {
    @NotNull
    private Long originId;
    @NotNull
    private OriginTypeEnum originType;

    /**
     * 目标任务类型标题(更新由触发器维护)
     */
    private String title;

    /**
     * 目标任务类型分值(更新由触发器维护)
     */
    private Integer score;

    /**
     * 目标任务类型发布状态(更新由触发器维护)
     */
    private Integer status;

    @NotNull
    private Long assignmentGroupId;
}
