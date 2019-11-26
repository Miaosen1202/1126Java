package com.wdcloud.lms.business.strategy.delete;

import com.wdcloud.lms.business.strategy.AbstractFileStrategy;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class FileDelete extends AbstractFileStrategy implements DeleteStrategy {
    @Override
    public Integer delete(Long id) {
        return userFileDao.delete(id);
    }

    @Override
    public Integer delete(Collection<Long> ids) {
        return userFileDao.deletes(ids);
    }
}
