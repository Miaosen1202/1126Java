package com.wdcloud.lms.business.strategy.delete;

import com.wdcloud.lms.business.strategy.AbstractExternalUrlStrategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assign;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class ExternalURLDelete extends AbstractExternalUrlStrategy implements DeleteStrategy {
    @Override
    public Integer delete(Long id) {

        assignDao.deleteByField(Assign.builder().originId(id).originType(OriginTypeEnum.EXTERNAL_URL.getType()).build());
        return externalUrlDao.delete(id);
    }

    @Override
    public Integer delete(Collection<Long> ids) {
        return externalUrlDao.deletes(ids);
    }
}
