package com.wdcloud.lms.business.module.dto;

import lombok.Data;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class ModuleItemMoveDTO {
    private Long sourceModuleId;
    private Long sourceModuleItemId;
    private Long targetModuleId;
    private Integer strategy;
    private Long targetModuleItemId;
}
