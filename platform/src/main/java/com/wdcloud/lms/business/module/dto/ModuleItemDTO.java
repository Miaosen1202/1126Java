package com.wdcloud.lms.business.module.dto;

import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * @author 赵秀非
 * @modifier WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class ModuleItemDTO {

    @NotNull(groups = GroupModify.class)
    private Long id;

    @Null(groups = GroupModify.class)
    @NotNull
    private Long moduleId;

    @Null(groups = GroupModify.class)
    @NotNull
    private Integer originType;

    @NotNull(groups = GroupModify.class)
    private Integer indentLevel;

    private BaseModuleItemDTO baseItemDTO;
}
