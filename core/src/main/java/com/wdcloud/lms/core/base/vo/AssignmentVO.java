package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Assignment;
import lombok.Data;

import java.util.Date;

@Data
public class AssignmentVO extends Assignment implements Comparable<AssignmentVO> {
    private Date dueTime;
    private String sectionName;
    private String userName;
    private Integer assignTableId;
    private Integer assignType;
    private Long roleType;

    @Override
    public int compareTo(AssignmentVO o) {
        return this.getTitle().compareTo(o.getTitle());
    }
}
