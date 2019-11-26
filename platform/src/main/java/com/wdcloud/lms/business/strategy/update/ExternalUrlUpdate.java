package com.wdcloud.lms.business.strategy.update;

import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.strategy.AbstractExternalUrlStrategy;
import com.wdcloud.lms.core.base.model.ExternalUrl;
import com.wdcloud.lms.core.base.model.TextHeader;
import com.wdcloud.server.frame.exception.ParamErrorException;
import com.wdcloud.utils.StringUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class ExternalUrlUpdate extends AbstractExternalUrlStrategy implements UpdateStrategy {

    @Override
    public void update(BaseModuleItemDTO baseModuleItemDTO) {

        if(StringUtil.isEmpty(baseModuleItemDTO.getUrl())){
            throw new ParamErrorException();
        }

        externalUrlDao.updateIncludeNull(ExternalUrl.builder().id(baseModuleItemDTO.getId())
                .pageName(baseModuleItemDTO.getName()).url(baseModuleItemDTO.getUrl()).build());
    }

    @Override
    public void updateNameAndUrl(Long id, String name, String url) {
        externalUrlDao.update(ExternalUrl.builder().id(id)
                .pageName(name).url(url).build());
    }

    @Override
    public List<CourseImportGenerationDTO> updateByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updatePublishStatus(Long id, Integer status) {
        //todo valid(id);
        externalUrlDao.update(ExternalUrl.builder().id(id).status(status).build());
    }

    @Override
    public void updateName(Long id, String name) {
        externalUrlDao.update(ExternalUrl.builder().id(id).pageName(name).build());
    }
}
