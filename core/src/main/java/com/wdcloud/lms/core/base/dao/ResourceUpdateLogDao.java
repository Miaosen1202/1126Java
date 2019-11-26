package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.enums.ResourceLogStatusEnum;
import com.wdcloud.lms.core.base.mapper.ext.ResourceUpdateLogExtMapper;
import com.wdcloud.lms.core.base.model.ResourceUpdateLog;
import com.wdcloud.lms.core.base.vo.resource.ResourceUpdateLogVO;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Repository
public class ResourceUpdateLogDao extends CommonDao<ResourceUpdateLog, Long> {

    @Autowired
    private ResourceUpdateLogExtMapper resourceUpdateLogExtMapper;

    @Override
    protected Class<ResourceUpdateLog> getBeanClass() {
        return ResourceUpdateLog.class;
    }

    public List<ResourceUpdateLogVO> getByAuthorId(Long authorId) {
        return resourceUpdateLogExtMapper.getByAuthorId(authorId);
    }

    public ResourceUpdateLog getLatestByStatusAndUserId(Integer status, Long userId){
        Example example = getExample();
        example.createCriteria()
                .andEqualTo(ResourceUpdateLog.STATUS, status)
                .andEqualTo(ResourceUpdateLog.USER_ID, userId);
        example.setOrderByClause(" create_time desc ");
        return findOne(example);
    }
}
