package com.wdcloud.lms.business.strategy.query;

import com.alibaba.fastjson.JSON;
import com.wdcloud.lms.business.resources.dto.AssignmentResourceShareDTO;
import com.wdcloud.lms.business.strategy.AbstractPageStrategy;
import com.wdcloud.lms.core.base.model.Page;
import org.springframework.stereotype.Component;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class PageQuery extends AbstractPageStrategy implements QueryStrategy {

    @SuppressWarnings("Duplicates")
    @Override
    public OriginData query(Long id) {
        Page page = pageDao.get(id);
        if (page != null) {
            return OriginData.builder()
                    .status(page.getStatus())
                    .originId(page.getId())
                    .originType(support().getType())
                    .title(page.getTitle())
                    .build();
        }
        return null;
    }

    @Override
    public Object queryDetail(Long id) {
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
