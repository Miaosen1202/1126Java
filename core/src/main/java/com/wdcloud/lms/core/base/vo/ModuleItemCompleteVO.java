package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Module;
import lombok.Data;

@Data
public class ModuleItemCompleteVO {
    private Long moduleItemId;
    private Integer completeStatus;
}
