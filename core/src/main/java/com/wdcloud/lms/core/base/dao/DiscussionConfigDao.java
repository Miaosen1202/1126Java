package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.DiscussionConfig;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.entity.Example;

@Repository
public class DiscussionConfigDao extends CommonDao<DiscussionConfig, Long> {

    public int update(long userId, long courseId, DiscussionConfig config) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo("courseId", courseId)
                .andEqualTo("userId", userId);
        return mapper.updateByExample(config, example);
    }

    public DiscussionConfig get(long userId, long courseId) {
        Example example = getExample();
        example.createCriteria()
                .andEqualTo("courseId", courseId)
                .andEqualTo("userId", userId);
        return findOne(example);
    }

    @Override
    protected Class<DiscussionConfig> getBeanClass() {
        return DiscussionConfig.class;
    }
}