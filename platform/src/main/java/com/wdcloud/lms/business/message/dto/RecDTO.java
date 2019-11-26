package com.wdcloud.lms.business.message.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecDTO {
    private Long allTeachers;
    private Long allStudents;
    private List<Long> sectionIds;
    private List<Long> groupIds;
    private List<Long> userIds;
}
