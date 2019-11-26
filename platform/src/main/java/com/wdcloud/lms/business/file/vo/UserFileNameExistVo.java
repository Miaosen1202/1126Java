package com.wdcloud.lms.business.file.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserFileNameExistVo {

    /**
     * 复制文件ID
     */
    private Long source;

    /**
     * 上级文件夹id(copy时上级文件夹id)
     */
    @NotNull
    private Long parentDirectoryId;

    /**
     * 是否是文件夹
     */
    @NotNull
    private Integer isDirectory;

    /**
     * 文件名字(新增是填写)
     */
    private String name;

    /**
     * 操作类型，add为1，copy为2
     */
    @NotNull
    private Integer operateType;
}
