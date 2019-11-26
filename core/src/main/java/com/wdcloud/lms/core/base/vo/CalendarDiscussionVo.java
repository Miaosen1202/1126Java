package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Discussion;
import lombok.Data;

import java.util.Date;

@Data
public class CalendarDiscussionVo  extends Discussion implements Comparable<CalendarDiscussionVo> {

    private Date dueTime;
    private String sectionName;
    private String userName;
    private Integer assignType;
    private Integer roleType;
    private Integer ownStudyGroupId;
    private Integer assignTableId;

    @Override
    public int compareTo(CalendarDiscussionVo o) {
        return getTitle().compareTo(o.getTitle());
    }
}
