package com.wdcloud.lms.business.strategy.update;

import com.wdcloud.lms.business.module.dto.BaseModuleItemDTO;
import com.wdcloud.lms.business.resources.dto.CourseImportGenerationDTO;
import com.wdcloud.lms.business.strategy.Strategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;

import java.util.List;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public interface UpdateStrategy extends Strategy {

    void update(BaseModuleItemDTO baseModuleItemDTO);

    default void updateName(Long id, String name) {
        throw new UnsupportedOperationException();
    }

    default void updatePublishStatus(Long id, Integer status) {
        throw new UnsupportedOperationException();
    }

    default void updateNameAndScoreAndStatus(Long id, String name, int score, boolean isPublish) {
        throw new UnsupportedOperationException();
    }

    default void updateNameAndUrl(Long id, String name, String url) {
        throw new UnsupportedOperationException();
    }

    List<CourseImportGenerationDTO> updateByResource(String beanJson, Long resourceId, List<Long> courseIds);

    OriginTypeEnum support();
}
