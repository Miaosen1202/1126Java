package com.wdcloud.lms.core.base.vo;

import com.wdcloud.lms.core.base.model.Course;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class CourseJoinedVo extends Course {

    private String coverFileUrl;
    private String courseAlias;
    private String coverColor;

    private Integer allowOpenRegistry;
    private String openRegistryCode;

    private Integer isFavorite;

    private Integer isActiveToUser;

    private Integer statusToUser;

    private Integer seq;

    private TermAndConfigVo term;

    private long gradeTaskSubmittedCount;
}
