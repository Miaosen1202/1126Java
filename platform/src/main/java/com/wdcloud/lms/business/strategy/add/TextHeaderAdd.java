package com.wdcloud.lms.business.strategy.add;

import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.module.dto.ModuleItemContentDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.strategy.AbstractTextHeaderStrategy;
import com.wdcloud.lms.core.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.TextHeader;
import com.wdcloud.lms.exceptions.PermissionException;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class TextHeaderAdd extends AbstractTextHeaderStrategy implements AddStrategy{

    @Override
    public Long add(ModuleItemContentDTO moduleItemContentDTO) {
        TextHeader subHeader = TextHeader.builder()
                .text(moduleItemContentDTO.getTitle())
                .status(Status.NO.getStatus())
                .build();
        textHeaderDao.save(subHeader);
        return subHeader.getId();
    }

    @Override
    public List<CourseImportGenerationDTO> addByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        throw new UnsupportedOperationException();
    }
}
