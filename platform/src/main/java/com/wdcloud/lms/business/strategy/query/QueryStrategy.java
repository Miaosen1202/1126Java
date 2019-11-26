package com.wdcloud.lms.business.strategy.query;

import com.wdcloud.lms.business.strategy.Strategy;
import com.wdcloud.lms.core.base.enums.OriginTypeEnum;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
public interface QueryStrategy<T> extends Strategy {
    OriginData query(Long id);

    T queryDetail(Long moduleItemId);

    OriginTypeEnum support();

    String queryResourceShareInfo(Long id, Long resourceId, String resourceName);

    T convertResourceObject(String beanJson);
}
