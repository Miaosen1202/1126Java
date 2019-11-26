package com.wdcloud.lms.business.announce.dto;

import com.wdcloud.lms.core.base.model.AnnounceReply;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AnnounceReplyAddDTO extends AnnounceReply {
    @NotNull(groups = GroupModify.class)
    private Long id;
    @NotNull(groups = GroupAdd.class)
    private Long announceId;
    @NotBlank(groups = {GroupAdd.class,GroupModify.class})
    private String content;

    private String fileId;//文件id
}
