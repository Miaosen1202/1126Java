package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.model.SisImportFile;
import org.springframework.stereotype.Repository;

@Repository
public class SisImportFileDao extends CommonDao<SisImportFile, Long> {

    @Override
    protected Class<SisImportFile> getBeanClass() {
        return SisImportFile.class;
    }
}