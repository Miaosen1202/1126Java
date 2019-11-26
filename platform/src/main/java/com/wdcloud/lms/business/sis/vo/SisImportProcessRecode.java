package com.wdcloud.lms.business.sis.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SisImportProcessRecode {
    private ProcessPhase phase;
    private int percent;
    private String batchCode;
    private int totalPercent;

    public enum ProcessPhase {
        /**
         * 文件检查
         */
        FILE_CHECK,
        /**
         * 保持到中间表
         */
        IMPORT,
        /**
         * 保存到业务表
         */
        TRANSFER;
    }
}
