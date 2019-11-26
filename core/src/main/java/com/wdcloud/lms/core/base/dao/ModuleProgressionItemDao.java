package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.ModuleProgressionItem;

public class ModuleProgressionItemDao extends CommonDao<ModuleProgressionItem, Long> {
    @Override
    protected Class<ModuleProgressionItem> getBeanClass() {
        return ModuleProgressionItem.class;
    }
}
