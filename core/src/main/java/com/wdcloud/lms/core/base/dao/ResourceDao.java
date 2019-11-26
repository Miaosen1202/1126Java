package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ResourceExtMapper;
import com.wdcloud.lms.core.base.model.Resource;
import com.wdcloud.lms.core.base.vo.resource.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResourceDao extends CommonDao<Resource, Long> {

    @Autowired
    private ResourceExtMapper resourceExtMapper;

    @Override
    protected Class<Resource> getBeanClass() {
        return Resource.class;
    }

    public Integer getOriginTypeById(Long id){
        Resource resource = this.get(id);
        return resource.getOriginType();
    }

    public ResourceVO getByIdAndImportUserId(Long id, Long importUserId){
        return resourceExtMapper.getByIdAndImportUserId(id, importUserId);
    }

    public List<ResourceVO> getByOriginIdAndOriginType(Long originId, Integer originType){
        return resourceExtMapper.getByOriginIdAndOriginType(originId, originType);
    }

    public List<ResourceVO> getByAuthorIdAndType(Long authorId, List<String> types) {
        return resourceExtMapper.getByAuthorIdAndType(authorId, types);
    }

    public List<ResourceVO> getByShareRangeAndCondition(Long authorId, List<String> types, Integer grade,
     String name, String rootOrgTreeId, String updateTime, String importCount) {
        return resourceExtMapper.getByShareRangeAndCondition(authorId, types, grade, name, rootOrgTreeId,
                updateTime, importCount);
    }

    public List<ResourceVO> getByShareRangeAndCondition(List<String> types, String name, String rootOrgTreeId, String updateTime, String importCount) {
        return resourceExtMapper.getByAdminRoleAndCondition(types, name, rootOrgTreeId,  updateTime, importCount);
    }
}
