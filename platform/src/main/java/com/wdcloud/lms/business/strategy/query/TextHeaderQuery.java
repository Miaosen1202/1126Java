package com.wdcloud.lms.business.strategy.query;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.business.resources.dto.AssignmentResourceShareDTO;
import com.wdcloud.lms.business.strategy.AbstractTextHeaderStrategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;
import com.wdcloud.lms.core.base.model.TextHeader;
import org.apache.ibatis.builder.ParameterExpression;
import org.springframework.stereotype.Component;

/**
 * @author WangYaRong
 */
@Component
public class TextHeaderQuery extends AbstractTextHeaderStrategy implements QueryStrategy {
    @Override
    public OriginData query(Long id) {
        TextHeader textHeader = textHeaderDao.get(id);
        if (textHeader != null) {
            return OriginData.builder()
                    .status(textHeader.getStatus())
                    .originId(textHeader.getId())
                    .originType(support().getType())
                    .title(textHeader.getText())
                    .build();
        }
        return null;
    }

    @Override
    public TextHeader queryDetail(Long id) {

        return textHeaderDao.get(id);
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
