package com.wdcloud.lms.business.strategy.query;

import com.wdcloud.lms.business.strategy.AbstractFileStrategy;
import com.wdcloud.lms.core.base.model.UserFile;
import com.wdcloud.lms.core.base.vo.FileDetailVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Component
public class FileQuery extends AbstractFileStrategy implements QueryStrategy {

    @SuppressWarnings("Duplicates")
    @Override
    public OriginData query(Long id) {
        UserFile file = userFileDao.get(id);
        if (file != null) {
            return OriginData.builder()
                    .status(file.getStatus())
                    .originId(file.getId())
                    .originType(support().getType())
                    .title(file.getFileName())
                    .build();
        }
        return null;
    }

    @Override
    public Object queryDetail(Long moduleItemId) {
        List<FileDetailVo> list = userFileDao.findFileDetailByModuleItem(moduleItemId);
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
