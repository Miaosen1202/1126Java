package com.wdcloud.lms.business.strategy.update;

import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.strategy.AbstractFileStrategy;
import com.wdcloud.lms.core.base.model.UserFile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class FileUpdate extends AbstractFileStrategy implements UpdateStrategy {
    @Override
    public void update(BaseModuleItemDTO baseModuleItemDTO) {
        updateName(baseModuleItemDTO.getId(), baseModuleItemDTO.getName());
    }

    @Override
    public void updateName(Long id, String name) {
        userFileDao.update(UserFile.builder().id(id).fileName(name).build());
    }

    @Override
    public void updatePublishStatus(Long id, Integer status) {
        userFileDao.update(UserFile.builder().id(id).status(status).build());
    }

    @Override
    public List<CourseImportGenerationDTO> updateByResource(String beanJson, Long resourceId, List<Long> courseIds) {
        throw new UnsupportedOperationException();
    }
}
