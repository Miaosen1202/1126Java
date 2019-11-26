package com.wdcloud.lms.business.module.dto;

import lombok.Data;

/**
 * @author 米照雅
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class ModuleItemContentDTO {
    private Long courseId;
    private Long moduleId;
    private Integer originType;
    private Integer indentLevel;
    private String title;
    private Long assignmentGroupId;
    private Integer isGrade;
    private Integer type;
    private Integer isDirectory;
    private String fileId;
    private Long parentDirectoryId;
    private Integer isCovered;
    private String url;
}
