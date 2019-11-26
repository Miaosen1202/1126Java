package com.wdcloud.lms.business.discussion.dto;

import com.wdcloud.lms.core.base.model.DiscussionReply;
import com.wdcloud.validate.groups.GroupAdd;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DiscussionReplyAddDTO extends DiscussionReply {
    @NotNull(groups = GroupModify.class)
    private Long id;

    @NotNull(groups ={ GroupAdd.class,GroupModify.class})
    private Long discussionId;

    @NotBlank(groups = {GroupAdd.class,GroupModify.class})
    private String content;

    private String fileId;
}
