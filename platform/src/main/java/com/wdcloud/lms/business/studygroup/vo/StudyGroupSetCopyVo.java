package com.wdcloud.lms.business.studygroup.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class StudyGroupSetCopyVo {
    @NotNull
    private Long id;
    @NotNull
    @Size(max = 100)
    private String name;
}
