package com.wdcloud.lms.business.file.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserFileMoveVo {
    @NotNull
    private Long source;
    @NotNull
    private Long target;

    /**
     * 文件的新的名字
     */
    private String newName;

    /**
     * 是否覆盖
     */
    private Integer isCovered;

}
