package com.wdcloud.lms.business.course.vo;

import com.wdcloud.lms.core.base.model.*;
import com.wdcloud.server.frame.interfaces.ISelfDefinedEdit;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CourseConfigVo extends CourseConfig {
    @NotNull(groups = ISelfDefinedEdit.class)
    private Course course;

    private String coverFileUrl;

    private DiscussionConfig discussionConfig;

    private UserConfig userConfig;

    private Integer isFavorite;
    private String coverColor;
    private String alias;

    private Term term;
    private Org org;
}
