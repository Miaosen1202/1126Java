package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.ModuleItem;
import lombok.Data;

@Data
public class FileDetailVo extends ModuleItem {
    private Long fileId;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private String fileUrl;
}
