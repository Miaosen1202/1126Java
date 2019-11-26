package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ResourceFavoriteExtMapper;
import com.wdcloud.lms.core.base.model.ResourceFavorite;
import com.wdcloud.lms.core.base.vo.resource.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResourceFavoriteDao extends CommonDao<ResourceFavorite, Long> {

    @Autowired
    private ResourceFavoriteExtMapper resourceFavoriteExtMapper;

    @Override
    protected Class<ResourceFavorite> getBeanClass() {
        return ResourceFavorite.class;
    }

    public List<ResourceVO> getByUserIdAndTypeAndShareRange(Long userId, List<String> types) {
        return resourceFavoriteExtMapper.getByUserIdAndTypeAndShareRange(userId, types);
    }
}
