package com.wdcloud.lms.business.resources.dto;

import lombok.Data;

@Data
public class AssignmentResourceShareDTO {

    private Long id;

    private String title;

    private String description;

    private Integer score;

    private Integer showScoreType;

    private Integer submissionType;

    private Integer allowText;

    private Integer allowUrl;

    private Integer allowMedia;

    private Integer allowFile;

    private String fileLimit;

    private Integer isIncludeGrade;
}
