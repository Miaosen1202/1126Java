package com.wdcloud.lms.business.resources.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class CourseImportGenerationDTO {

    private Long courseId;

    private Long newId;

    private Long originId;

    private Integer originType;
}
