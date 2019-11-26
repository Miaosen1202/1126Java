package com.wdcloud.lms.business.course.vo;

import com.wdcloud.lms.core.base.model.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CourseVo extends Course {
    private int announceNumber;
    private int unreadAnnounceNumber;

    private int discussionNumber;
    private int unreadDiscussionNumber;

    private int assignmentGroupItemNumber;

    private int courseFileNumber;

    private static final long serialVersionUID = -1;
}
