package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.SisImportUser;

import java.util.List;

public interface SisImportUserExtMapper {
    /**
     * 批量保存或更新需导入的人员
     * @param importUsers 需导入的人员
     */
    void batchSaveOrUpdate(List<SisImportUser> importUsers);
}
