package com.wdcloud.lms.core.base.mapper.ext;

import com.wdcloud.lms.core.base.model.SisImportError;

import java.util.List;

public interface SisImportErrorExtMapper {

    /**
     * 批量保存批量导入时产生的错误信息
     * @param errors 错误信息
     */
    void batchSave(List<SisImportError> errors);
}
