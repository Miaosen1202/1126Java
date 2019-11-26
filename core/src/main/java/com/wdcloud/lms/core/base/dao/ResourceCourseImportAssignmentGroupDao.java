package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.ResourceCourseImportAssignmentGroup;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceCourseImportAssignmentGroupDao extends CommonDao<ResourceCourseImportAssignmentGroup, Long> {
    @Override
    protected Class<ResourceCourseImportAssignmentGroup> getBeanClass() {
        return ResourceCourseImportAssignmentGroup.class;
    }

    public ResourceCourseImportAssignmentGroup getByCourseId(Long courseId) {
        ResourceCourseImportAssignmentGroup courseGroup = ResourceCourseImportAssignmentGroup.builder()
                .courseId(courseId).build();
        return this.findOne(courseGroup);
    }
}
