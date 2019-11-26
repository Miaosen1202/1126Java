package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.SisImportTerm;

import java.util.List;

public interface SisImportTermExtMapper {
    /**
     * 批量保存或更新需导入的学期
     * @param sisImportTerms 需导入的学期
     */
    void batchSaveOrUpdate(List<SisImportTerm> sisImportTerms);
}
