package com.wdcloud.lms.business.module.dto;

import com.wdcloud.lms.core.base.model.ModulePrerequisite;
import com.wdcloud.lms.core.base.model.ModuleRequirement;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
public class ModuleItemCompleteDTO {

    /**
     * 单元项内容ID
     */
    @NotNull
    private Long originId;

    /**
     * 单元项类型
     */
    @NotNull
    private Integer originType;
}
