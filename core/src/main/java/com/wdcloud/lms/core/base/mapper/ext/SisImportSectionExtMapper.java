package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.SisImportSection;

import java.util.List;

public interface SisImportSectionExtMapper {

    /**
     * 批量保存或更新需导入的班级
     * @param importSections 需导入的班级
     */
    void batchSaveOrUpdate(List<SisImportSection> importSections);
}
