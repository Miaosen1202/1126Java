package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.SisImportStudyGroup;

import java.util.List;

public interface SisImportStudyGroupExtMapper {
    /**
     * 批量保存或更新需导入的学习小组
     * @param sisImportStudyGroups 需导入的学习小组
     */
    void batchSaveOrUpdate(List<SisImportStudyGroup> sisImportStudyGroups);
}
