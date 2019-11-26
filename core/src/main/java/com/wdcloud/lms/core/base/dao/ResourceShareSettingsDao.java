package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.ResourceShareSetting;
import com.wdcloud.lms.core.base.vo.resource.ResourceShareSettingVO;
import org.springframework.stereotype.Repository;

@Repository
public class ResourceShareSettingsDao extends CommonDao<ResourceShareSetting, Long> {

    @Override
    protected Class<ResourceShareSetting> getBeanClass() {
        return ResourceShareSetting.class;
    }

    public ResourceShareSetting findByOrgId(Long orgId) {
        ResourceShareSetting resourceShareSetting = ResourceShareSetting.builder().orgId(orgId).build();
        return findOne(resourceShareSetting);
    }
}
