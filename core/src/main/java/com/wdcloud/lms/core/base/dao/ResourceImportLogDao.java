package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ResourceImportedLogExtMapper;
import com.wdcloud.lms.core.base.model.ResourceImport;
import com.wdcloud.lms.core.base.model.ResourceImportedLog;
import com.wdcloud.lms.core.base.vo.resource.ResourceImportLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository
public class ResourceImportLogDao extends CommonDao<ResourceImportedLog, Long> {

    @Autowired
    private ResourceImportedLogExtMapper resourceImportedLogExtMapper;

    @Override
    protected Class<ResourceImportedLog> getBeanClass() {
        return ResourceImportedLog.class;
    }

    public List<ResourceImportLogVO> getByImportUserId(Long userId){
        return resourceImportedLogExtMapper.getByImportUserId(userId);
    }

    public List<ResourceImportedLog> getLatestByStatusAndUserId(Integer status, Long userId){
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(ResourceImportedLog.STATUS, status)
                .andEqualTo(ResourceImportedLog.USER_ID, userId);
        return find(example);
    }
}
