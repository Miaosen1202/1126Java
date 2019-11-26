package com.wdcloud.lms.business.strategy.delete;

import com.wdcloud.lms.business.strategy.AbstractPageStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class PageDelete extends AbstractPageStrategy implements DeleteStrategy {

    @Override
    public Integer delete(Long id) {
        return pageDao.delete(id);
    }

    @Override
    public Integer delete(Collection<Long> ids) {
        return pageDao.deletes(ids);
    }
}
