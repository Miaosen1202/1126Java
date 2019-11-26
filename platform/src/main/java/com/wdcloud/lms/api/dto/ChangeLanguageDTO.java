package com.wdcloud.lms.api.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wangff
 * @date 2019/11/21 17:01
 */
@Data
public class ChangeLanguageDTO {
    @NotBlank
    private String  language;
}
