package com.wdcloud.lms.business.sis.vo;

import com.wdcloud.lms.core.base.model.SisImport;
import com.wdcloud.lms.core.base.model.SisImportError;
import com.wdcloud.lms.core.base.model.SisImportFile;
import lombok.Data;

import java.util.List;

@Data
public class SisImportVo extends SisImport {
    private List<SisImportError> errors;
    private List<SisImportFile> importFiles;
}
