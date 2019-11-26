package com.wdcloud.lms.business.resources.vo;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class ResourceCourseItemVO {

    private Long id;

    private String title;

    private Integer score;

    private Integer questionCount;
}
