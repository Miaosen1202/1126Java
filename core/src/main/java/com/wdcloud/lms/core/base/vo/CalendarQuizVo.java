package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Quiz;
import lombok.Data;

import java.util.Date;

@Data
public class CalendarQuizVo extends Quiz implements Comparable<CalendarQuizVo>{
    private Date dueTime;
    private String sectionName;
    private String userName;
    private Integer assignType;
    private Integer assignTableId;

    @Override
    public int compareTo(CalendarQuizVo o) {
        return getTitle().compareTo(o.getTitle());
    }
}
