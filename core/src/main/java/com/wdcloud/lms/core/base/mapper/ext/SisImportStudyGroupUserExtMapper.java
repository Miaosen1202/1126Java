package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.SisImportStudyGroupUser;

import java.util.List;

public interface SisImportStudyGroupUserExtMapper {
    /**
     *  批量保存需导入学习小组-人员中间表信息
     * @param studyGroupUsers 需导入学习小组-人员中间表信息
     */
    void batchSave(List<SisImportStudyGroupUser> studyGroupUsers);
}
