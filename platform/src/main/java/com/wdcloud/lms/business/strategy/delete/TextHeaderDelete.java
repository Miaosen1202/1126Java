package com.wdcloud.lms.business.strategy.delete;

import com.wdcloud.lms.business.strategy.AbstractTextHeaderStrategy;
import com.wdcloud.lms.business.strategy.query.QueryStrategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.Assign;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class TextHeaderDelete extends AbstractTextHeaderStrategy implements DeleteStrategy {

    @Override
    public Integer delete(Long id) {

        assignDao.deleteByField(Assign.builder().originId(id).originType(OriginTypeEnum.TEXT_HEADER.getType()).build());
        return textHeaderDao.delete(id);
    }

    @Override
    public Integer delete(Collection<Long> ids) {
        return textHeaderDao.deletes(ids);
    }

}
