package com.wdcloud.lms.business.strategy.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OriginData {
    private Long originId;
    private Integer originType;
    private String title;
    private Integer score;
    private Integer status;
}
