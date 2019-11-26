package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.SisImportOrg;

import java.util.List;

public interface SisImportOrgExtMapper {

    /**
     * 批量保存需导入的机构
     * @param orgs 需导入的机构
     */
    void batchSave(List<SisImportOrg> orgs);

    /**
     * 批量保存或更新需导入的机构
     * @param importOrgs 需导入的机构
     */
    void batchSaveOrUpdate(List<SisImportOrg> importOrgs);
}
