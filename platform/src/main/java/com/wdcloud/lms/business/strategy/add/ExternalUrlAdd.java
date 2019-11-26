package com.wdcloud.lms.business.strategy.add;

import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.module.dto.ModuleItemContentDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.strategy.AbstractExternalUrlStrategy;
import com.wdcloud.lms.core.base.enums.AssignTypeEnum;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.enums.Status;
import com.wdcloud.lms.core.base.model.Assign;
import com.wdcloud.lms.core.base.model.ExternalUrl;
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
public class ExternalUrlAdd extends AbstractExternalUrlStrategy implements AddStrategy {

    @Override
    public Long add(ModuleItemContentDTO moduleItemContentDTO) {
        //0、参数解析
        if (null == moduleItemContentDTO.getUrl()) {
            throw new ParamErrorException();
        }
        //1、添加外部链接
        ExternalUrl webSiteUrl = ExternalUrl.builder()
                .url(moduleItemContentDTO.getUrl())
                .pageName(moduleItemContentDTO.getTitle())
                .status(Status.NO.getStatus())
                .build();
        externalUrlDao.save(webSiteUrl);
        return webSiteUrl.getId();
    }

    @Override
    public List<CourseImportGenerationDTO> addByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        throw new UnsupportedOperationException();
    }
}
