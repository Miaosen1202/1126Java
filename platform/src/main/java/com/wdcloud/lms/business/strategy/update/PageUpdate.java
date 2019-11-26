package com.wdcloud.lms.business.strategy.update;

import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.strategy.AbstractPageStrategy;
import com.wdcloud.lms.core.base.model.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class PageUpdate extends AbstractPageStrategy implements UpdateStrategy {
    @Override
    public void update(BaseModuleItemDTO baseModuleItemDTO) {
        updateName(baseModuleItemDTO.getId(), baseModuleItemDTO.getName());
    }

    @Override
    public void updateName(Long id, String name) {
        pageDao.update(Page.builder().id(id).title(name).build());
    }

    @Override
    public void updatePublishStatus(Long id, Integer status) {
        pageDao.update(Page.builder().id(id).status(status).build());
    }

    @Override
    public List<CourseImportGenerationDTO> updateByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        throw new UnsupportedOperationException();
    }
}
