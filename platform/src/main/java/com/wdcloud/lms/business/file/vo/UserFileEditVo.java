package com.wdcloud.lms.business.file.vo;

import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class UserFileEditVo {
    @NotNull(groups = {GroupModify.class})
    private Long id;
    @NotNull(groups = {GroupAdd.class, GroupModify.class})
    private String name;
    private Integer isDirectory;
    private String fileId;
    private Long parentDirectoryId;
    private Integer isCovered;
    private Integer status;
}
