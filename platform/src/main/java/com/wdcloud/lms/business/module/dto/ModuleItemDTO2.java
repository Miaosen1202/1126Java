package com.wdcloud.lms.business.module.dto;

import com.wdcloud.lms.core.base.model.ModuleItem;
import lombok.Data;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class ModuleItemDTO2 extends ModuleItem {

    private Long[] originIds;
}
