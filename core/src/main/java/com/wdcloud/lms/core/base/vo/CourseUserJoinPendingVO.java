package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Course;
import com.wdcloud.lms.core.base.model.CourseUserJoinPending;
import com.wdcloud.lms.core.base.model.PRole;
import com.wdcloud.lms.core.base.model.Section;
import lombok.Data;

@Data
public class CourseUserJoinPendingVO extends CourseUserJoinPending {
    /**
     * 课程
     */
    private Course course;
    /**
     * 班级
     */
    private Section section;
    /**
     * 角色
     */
    private PRole pRole;
    /**
     * 邀请状态，0为接受邀请，1为拒接邀请
     */
    private Integer registryStatus;
}
