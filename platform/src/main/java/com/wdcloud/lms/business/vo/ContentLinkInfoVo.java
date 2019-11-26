package com.wdcloud.lms.business.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentLinkInfoVo {
    private Long courseId;
    private Long id;
    private String name;
}
