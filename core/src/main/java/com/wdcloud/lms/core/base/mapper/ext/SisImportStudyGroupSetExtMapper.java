package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.SisImportStudyGroupSet;

import java.util.List;

public interface SisImportStudyGroupSetExtMapper {
    /**
     * 批量导入或更新需导入的小组集
     * @param studyGroupSets 需导入的小组集
     */
    void batchSaveOrUpdate(List<SisImportStudyGroupSet> studyGroupSets);
}
