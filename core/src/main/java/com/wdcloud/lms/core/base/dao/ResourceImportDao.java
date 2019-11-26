package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ResourceImportExtMapper;
import com.wdcloud.lms.core.base.model.ResourceImport;
import com.wdcloud.lms.core.base.vo.resource.ResourceCourseVO;
import com.wdcloud.lms.core.base.vo.resource.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository
public class ResourceImportDao extends CommonDao<ResourceImport, Long> {

    @Autowired
    private ResourceImportExtMapper resourceImportExtMapper;

    @Override
    protected Class<ResourceImport> getBeanClass() {
        return ResourceImport.class;
    }

    public List<ResourceVO> getByUserIdAndTypeAndVersionMessageSort(Long userId, List<String> types) {
        return resourceImportExtMapper.getByUserIdAndTypeAndVersionMessageSort(userId, types);
    }

    public List<ResourceCourseVO> getCourseByResourceIdAndUserId(Long resourceId, Long userId) {
        return resourceImportExtMapper.getCourseByResourceIdAndUserId(resourceId, userId);
    }

    public List<ResourceCourseVO> getCourseByNotInResourceAndUserId(Long resourceId, Long userId) {
        return resourceImportExtMapper.getCourseByNotInResourceAndUserId(resourceId, userId);
    }

    public ResourceImport getResourceIdAndCourseId(Long resourceId, Long courseId) {
        ResourceImport resourceImport = ResourceImport.builder().resourceId(resourceId)
                .courseId(courseId).build();
        return this.findOne(resourceImport);
    }
}
