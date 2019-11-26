package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Module;
import com.wdcloud.lms.core.base.model.Resource;
import lombok.Data;

@Data
public class ModuleVO extends Module {
    private int itemsCount;
    private Integer completeStatus;
}
