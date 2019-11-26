package com.wdcloud.lms.business.module.vo;

import com.wdcloud.lms.core.base.model.Module;
import lombok.Data;

import java.util.List;

@Data
public class ModuleVO extends Module {
    private List<Module> prerequisites;
}
