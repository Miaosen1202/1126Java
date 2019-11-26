package com.wdcloud.lms.business.module.vo;

import com.wdcloud.lms.core.base.model.ModuleItem;
import lombok.Builder;
import lombok.Data;

/**
 * @author WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class ModuleItemDetailVO<T> {

    private T moduleItem;

    private ModuleItem previous;

    private ModuleItem next;

}
