package com.wdcloud.lms.core.base.dao;

import com.wdcloud.common.dao.CommonDao;
import com.wdcloud.lms.core.base.mapper.ext.SisImportErrorExtMapper;
import com.wdcloud.lms.core.base.model.SisImportError;
import com.wdcloud.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SisImportErrorDao extends CommonDao<SisImportError, Long> {

    @Autowired
    private SisImportErrorExtMapper sisImportErrorExtMapper;

    public void batchSave(List<SisImportError> errors) {
        if (ListUtils.isNotEmpty(errors)) {
            sisImportErrorExtMapper.batchSave(errors);
        }
    }

    @Override
    protected Class<SisImportError> getBeanClass() {
        return SisImportError.class;
    }
}