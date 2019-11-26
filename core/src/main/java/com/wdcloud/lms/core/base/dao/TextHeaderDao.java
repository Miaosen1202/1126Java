package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.TextHeader;
import org.springframework.stereotype.Repository;

/**
 * @author WangYaRong
 */
@Repository
public class TextHeaderDao extends CommonDao<TextHeader, Long> {

    @Override
    protected Class<TextHeader> getBeanClass() {return TextHeader.class;}
}
