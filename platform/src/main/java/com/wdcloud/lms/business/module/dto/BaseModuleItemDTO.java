package com.wdcloud.lms.business.module.dto;

import lombok.Data;

/**
 * @author  WangYaRong
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class BaseModuleItemDTO {
    private Long id;

    /**
     * sub header, page name, assign name， quiz name，page name of url
     */
    private String name;

    private String url;

    /**
     * assignment, quiz type, discussion type
     */
    private Long assignmentGroupId;

    /**
     * quiz type, discussion type
     */
    private Integer type;

    /**
     * 提交文件id
     */
    private String fieldId;
}
