package com.wdcloud.lms.business.strategy.update;

import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.strategy.AbstractTextHeaderStrategy;
import com.wdcloud.lms.core.base.model.TextHeader;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class TextHeaderUpdate extends AbstractTextHeaderStrategy implements UpdateStrategy {

    @Override
    public void update(BaseModuleItemDTO baseModuleItemDTO) {
        updateName(baseModuleItemDTO.getId(), baseModuleItemDTO.getName());
    }

    @Override
    public void updateName(Long id, String name) {
        textHeaderDao.update(TextHeader.builder().id(id).text(name).build());
    }

    @Override
    public void updatePublishStatus(Long id, Integer status) {
        //todo valid(id);
        textHeaderDao.update(TextHeader.builder().id(id).status(status).build());
    }

    @Override
    public List<CourseImportGenerationDTO> updateByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        throw new UnsupportedOperationException();
    }

}
