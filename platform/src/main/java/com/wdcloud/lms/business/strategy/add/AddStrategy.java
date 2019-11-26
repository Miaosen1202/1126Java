package com.wdcloud.lms.business.strategy.add;

import com.wdcloud.lms.business.module.dto.ModuleItemContentDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.strategy.Strategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;

import java.util.List;

/**
 * @author WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public interface AddStrategy extends Strategy {

    Long add(ModuleItemContentDTO moduleItemContentDTO);

    OriginTypeEnum support();

    List<CourseImportGenerationDTO> addByResource(String beanJson, Long resourceId, List<Long> courseIds);
}
