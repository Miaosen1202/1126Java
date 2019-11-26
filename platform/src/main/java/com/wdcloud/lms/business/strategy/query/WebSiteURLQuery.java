package com.wdcloud.lms.business.strategy.query;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.business.resources.dto.AssignmentResourceShareDTO;
import com.wdcloud.lms.business.strategy.AbstractExternalUrlStrategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.TextHeader;
import com.wdcloud.lms.core.base.model.ExternalUrl;
import com.wdcloud.lms.core.base.vo.ExternalUrlDetailVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author WangYaRong
 */
@Component
public class WebSiteURLQuery extends AbstractExternalUrlStrategy implements QueryStrategy{
    @Override
    public OriginData query(Long id) {
        ExternalUrl externalUrl = externalUrlDao.get(id);
        if (externalUrl != null) {
            return OriginData.builder()
                    .status(externalUrl.getStatus())
                    .originId(externalUrl.getId())
                    .originType(support().getType())
                    .title(externalUrl.getPageName())
                    .build();
        }
        return null;
    }

    @Override
    public ExternalUrlDetailVo queryDetail(Long moduleItemId) {
        List<ExternalUrlDetailVo> list = externalUrlDao.findExternalUrlByModuleItem(moduleItemId);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public String queryResourceShareInfo(Long id, Long resourceId, String resourceName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object convertResourceObject(String beanJson) {
        throw new UnsupportedOperationException();
    }

}
