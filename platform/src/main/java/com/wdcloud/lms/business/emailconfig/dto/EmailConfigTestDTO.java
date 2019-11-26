package com.wdcloud.lms.business.emailconfig.dto;

import lombok.Data;

/**
 * @author wangff
 * @date 2019/8/1 15:06
 */
@Data
public class EmailConfigTestDTO {
    private Integer testType;
    private String recEmail;
    private String subject;
    private String content;

}
