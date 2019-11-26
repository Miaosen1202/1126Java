package com.wdcloud.lms.business.module.dto;

import com.wdcloud.lms.core.base.model.ModulePrerequisite;
import com.wdcloud.lms.core.base.model.ModuleRequirement;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class ModuleDTO {
    private Long id;

    /**
     * 课程ID
     */
    @NotNull
    private Long courseId;

    /**
     * 名称
     */
    @NotNull
    private String name;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 单元要求完成策略，1：完成所有要求， 2： 按顺序完成所有要求， 3：完成任意一个
     */
    private Integer completeStrategy;

    /**
     * 发布状态
     */
    private Integer status;
    // 先决条件
    private List<ModulePrerequisite> prerequisites;

    // 完成要求
    private List<ModuleRequirement> requirements;
}
