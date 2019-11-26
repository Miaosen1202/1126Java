package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.SisImportSectionUser;

import java.util.List;

public interface SisImportSectionUserExtMapper {
    /**
     * 批量保存或更新需导入的班级人员
     * @param sectionUsers 需导入的班级人员
     */
    void batchSaveOrUpdate(List<SisImportSectionUser> sectionUsers);
}
