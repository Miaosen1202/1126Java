package com.wdcloud.lms.business.module.dto;

import lombok.Data;

/**
 * @author 赵秀非
 */
@SuppressWarnings({"JavaDoc", "SpringJavaAutowiredFieldsWarningInspection"})
@Data
public class ModuleItemIndentDTO {
    private long id;
    private int  indent;
    private String  name;
    private String url;
}
