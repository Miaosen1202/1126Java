package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.ExternalUrlExtMapper;
import com.wdcloud.lms.core.base.model.ExternalUrl;
import com.wdcloud.lms.core.base.vo.ExternalUrlDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author WangYaRong
 */
@Repository
public class ExternalUrlDao extends CommonDao<ExternalUrl, Long> {

    @Autowired
    private ExternalUrlExtMapper externalUrlExtMapper;

    public List<ExternalUrlDetailVo> findExternalUrlByModuleItem(Long moduleItemId) {
        return externalUrlExtMapper.findExternalUrlByModuleItem(moduleItemId);
    }

    @Override
    protected Class<ExternalUrl> getBeanClass() { return ExternalUrl.class;}


}
