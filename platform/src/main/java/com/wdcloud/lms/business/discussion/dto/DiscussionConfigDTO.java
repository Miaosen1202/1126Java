package com.wdcloud.lms.business.discussion.dto;

import com.wdcloud.lms.core.base.model.DiscussionConfig;
import com.wdcloud.validate.groups.GroupModify;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class DiscussionConfigDTO extends DiscussionConfig {
    @NotNull(groups = GroupModify.class)
    private Long id;
    @NotNull(groups = GroupModify.class)
    private Long courseId;
}
